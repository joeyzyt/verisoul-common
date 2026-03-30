// SPDX-License-Identifier: MIT
pragma solidity ^0.8.13;

contract SBT {
    string public name;
    string public ticker;
    address public operator;

    struct Soul {
        uint256 profileId;
        string identity;
        string url;
        uint256 score;
        uint256 timestamp;
        bool isRevoked;
    }

    // soul address => Soul data
    mapping(address => Soul) private souls;

    // soul address => list of profiler addresses
    mapping(address => address[]) private soulProfilers;

    // profiler => soul => Soul[] profiles
    mapping(address => mapping(address => Soul[])) private profiles;

    // profiler => soul => identity hash => index+1 in profiles array (0 means not found)
    mapping(address => mapping(address => mapping(bytes32 => uint256))) private profileIndex;

    bytes32 private emptyStringHash;

    event Mint(address _soul);
    event Burn(address _soul);
    event Update(address _soul);
    event SetProfile(address _profiler, address _soul, string identity);
    event RemoveProfile(address _profiler, address _soul, string identity);

    constructor(string memory _name, string memory _ticker) {
        name = _name;
        ticker = _ticker;
        operator = msg.sender;
        emptyStringHash = keccak256(abi.encodePacked(""));
    }

    modifier onlyOperator() {
        require(msg.sender == operator, "Only Relayer can execute this");
        _;
    }

    // ---- Soul lifecycle ----

    function mint(address _soul, Soul memory _soulData) external onlyOperator {
        require(
            keccak256(abi.encodePacked(souls[_soul].identity)) == emptyStringHash,
            "Soul already exists"
        );
        souls[_soul] = _soulData;
        emit Mint(_soul);
    }

    function update(address _soul, Soul memory _soulData, address _operator) external onlyOperator {
        require(_operator == _soul, "Only the soul owner can update their data");
        require(
            keccak256(abi.encodePacked(souls[_soul].identity)) != emptyStringHash,
            "Soul does not exist"
        );
        souls[_soul] = _soulData;
        emit Update(_soul);
    }

    function burn(address _soul, address _operator) external onlyOperator {
        require(_operator == _soul, "Only the soul owner can burn their data");

        delete souls[_soul];

        // Clean up all profiles issued to this soul
        for (uint256 i = 0; i < soulProfilers[_soul].length; i++) {
            address profiler = soulProfilers[_soul][i];
            delete profiles[profiler][_soul];
        }
        delete soulProfilers[_soul];

        emit Burn(_soul);
    }

    function getSoul(address _soul) external view returns (Soul memory) {
        return souls[_soul];
    }

    function hasSoul(address _soul) external view returns (bool) {
        return keccak256(abi.encodePacked(souls[_soul].identity)) != emptyStringHash;
    }

    // ---- Profile management ----

    function setProfile(
        address _profiler,
        address _soul,
        Soul memory _soulData,
        address _operator
    ) external onlyOperator {
        require(
            keccak256(abi.encodePacked(souls[_soul].identity)) != emptyStringHash,
            "Cannot create a profile for a soul that has not been minted"
        );

        bytes32 idHash = keccak256(abi.encodePacked(_soulData.identity));
        uint256 existingIdx = profileIndex[_profiler][_soul][idHash];

        if (existingIdx > 0) {
            // Update existing profile
            profiles[_profiler][_soul][existingIdx - 1] = _soulData;
        } else {
            // Add new profile
            profiles[_profiler][_soul].push(_soulData);
            profileIndex[_profiler][_soul][idHash] = profiles[_profiler][_soul].length;

            // Track profiler for this soul (only on first profile from this profiler)
            if (profiles[_profiler][_soul].length == 1) {
                soulProfilers[_soul].push(_profiler);
            }
        }

        emit SetProfile(_profiler, _soul, _soulData.identity);
    }

    function removeProfile(
        address _profiler,
        address _soul,
        string memory _identity,
        address _operator
    ) external onlyOperator {
        require(
            _operator == _profiler || _operator == _soul,
            "Only profiler or soul can remove profile"
        );

        bytes32 idHash = keccak256(abi.encodePacked(_identity));
        uint256 idx = profileIndex[_profiler][_soul][idHash];
        require(idx > 0, "Profile with that identity not found");

        uint256 arrayIdx = idx - 1;
        Soul[] storage arr = profiles[_profiler][_soul];
        uint256 lastIdx = arr.length - 1;

        if (arrayIdx != lastIdx) {
            // Swap with last element
            arr[arrayIdx] = arr[lastIdx];
            bytes32 movedHash = keccak256(abi.encodePacked(arr[arrayIdx].identity));
            profileIndex[_profiler][_soul][movedHash] = idx;
        }

        arr.pop();
        delete profileIndex[_profiler][_soul][idHash];

        emit RemoveProfile(_profiler, _soul, _identity);
    }

    function revokeProfile(
        address _profiler,
        address _soul,
        string memory _identity,
        address _operator
    ) external onlyOperator {
        require(
            _operator == _profiler || _operator == _soul,
            "Only profiler or soul can revoke profile"
        );

        bytes32 idHash = keccak256(abi.encodePacked(_identity));
        uint256 idx = profileIndex[_profiler][_soul][idHash];
        require(idx > 0, "Profile with that identity not found");

        profiles[_profiler][_soul][idx - 1].isRevoked = true;

        emit RemoveProfile(_profiler, _soul, _identity);
    }

    function checkRevocation(
        address _profiler,
        address _soul,
        string memory _identity
    ) external view returns (bool) {
        bytes32 idHash = keccak256(abi.encodePacked(_identity));
        uint256 idx = profileIndex[_profiler][_soul][idHash];
        if (idx == 0) return false;
        return profiles[_profiler][_soul][idx - 1].isRevoked;
    }

    function getProfilesByProfiler(
        address _profiler,
        address _soul
    ) external view returns (Soul[] memory) {
        return profiles[_profiler][_soul];
    }

    function hasProfile(address _profiler, address _soul) external view returns (bool) {
        return profiles[_profiler][_soul].length > 0;
    }

    function listProfiles(
        address _soul
    ) external view returns (Soul[] memory, address[] memory) {
        address[] memory profilerList = soulProfilers[_soul];

        // Count total profiles
        uint256 total = 0;
        for (uint256 i = 0; i < profilerList.length; i++) {
            total += profiles[profilerList[i]][_soul].length;
        }

        Soul[] memory allSouls = new Soul[](total);
        address[] memory allIssuers = new address[](total);

        uint256 idx = 0;
        for (uint256 i = 0; i < profilerList.length; i++) {
            address profiler = profilerList[i];
            Soul[] storage pSouls = profiles[profiler][_soul];
            for (uint256 j = 0; j < pSouls.length; j++) {
                allSouls[idx] = pSouls[j];
                allIssuers[idx] = profiler;
                idx++;
            }
        }

        return (allSouls, allIssuers);
    }
}
