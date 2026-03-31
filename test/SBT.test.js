const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("SBT Contract", function () {
  let sbt;
  let operator, soul1, soul2, profiler1, profiler2, stranger;

  function makeSoul(identity, url, score = 0, timestamp = 0, profileId = 0, isRevoked = false) {
    return { profileId, identity, url, score, timestamp, isRevoked };
  }

  beforeEach(async function () {
    [operator, soul1, soul2, profiler1, profiler2, stranger] = await ethers.getSigners();
    const SBT = await ethers.getContractFactory("SBT");
    sbt = await SBT.deploy("Verisoul SBT", "VSBT");
  });

  // ==============================================================
  // Constructor
  // ==============================================================

  describe("Constructor", function () {
    it("sets name, ticker, and operator", async function () {
      expect(await sbt.name()).to.equal("Verisoul SBT");
      expect(await sbt.ticker()).to.equal("VSBT");
      expect(await sbt.operator()).to.equal(operator.address);
    });
  });

  // ==============================================================
  // Soul Lifecycle: mint, getSoul, hasSoul, update, burn
  // ==============================================================

  describe("Soul Lifecycle", function () {
    it("mint creates a soul", async function () {
      const data = makeSoul("alice", "ipfs://Qm1", 80, 1700000000);
      await expect(sbt.mint(soul1.address, data)).to.emit(sbt, "Mint").withArgs(soul1.address);

      expect(await sbt.hasSoul(soul1.address)).to.be.true;
      const s = await sbt.getSoul(soul1.address);
      expect(s.identity).to.equal("alice");
      expect(s.url).to.equal("ipfs://Qm1");
      expect(s.score).to.equal(80n);
    });

    it("double mint is rejected", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      await expect(sbt.mint(soul1.address, makeSoul("alice2", "url2")))
        .to.be.revertedWith("Soul already exists");
    });

    it("hasSoul returns false for unregistered address", async function () {
      expect(await sbt.hasSoul(soul1.address)).to.be.false;
    });

    it("getSoul returns empty struct for unregistered address", async function () {
      const s = await sbt.getSoul(soul1.address);
      expect(s.identity).to.equal("");
    });

    it("update changes soul data", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url1", 80));
      const updated = makeSoul("alice", "url2", 95, 1700000001);
      await expect(sbt.update(soul1.address, updated, soul1.address))
        .to.emit(sbt, "Update").withArgs(soul1.address);

      const s = await sbt.getSoul(soul1.address);
      expect(s.url).to.equal("url2");
      expect(s.score).to.equal(95n);
    });

    it("update rejects non-owner operator", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      await expect(sbt.update(soul1.address, makeSoul("alice", "url2"), soul2.address))
        .to.be.revertedWith("Only the soul owner can update their data");
    });

    it("update rejects nonexistent soul", async function () {
      await expect(sbt.update(soul1.address, makeSoul("alice", "url"), soul1.address))
        .to.be.revertedWith("Soul does not exist");
    });

    it("burn deletes soul", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      await expect(sbt.burn(soul1.address, soul1.address))
        .to.emit(sbt, "Burn").withArgs(soul1.address);

      expect(await sbt.hasSoul(soul1.address)).to.be.false;
    });

    it("burn rejects non-owner", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      await expect(sbt.burn(soul1.address, soul2.address))
        .to.be.revertedWith("Only the soul owner can burn their data");
    });
  });

  // ==============================================================
  // Access Control
  // ==============================================================

  describe("Access Control", function () {
    it("non-operator cannot mint", async function () {
      const sbtAsStranger = sbt.connect(stranger);
      await expect(sbtAsStranger.mint(soul1.address, makeSoul("alice", "url")))
        .to.be.revertedWith("Only Relayer can execute this");
    });

    it("non-operator cannot update", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      const sbtAsStranger = sbt.connect(stranger);
      await expect(sbtAsStranger.update(soul1.address, makeSoul("alice", "url2"), soul1.address))
        .to.be.revertedWith("Only Relayer can execute this");
    });

    it("non-operator cannot burn", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      const sbtAsStranger = sbt.connect(stranger);
      await expect(sbtAsStranger.burn(soul1.address, soul1.address))
        .to.be.revertedWith("Only Relayer can execute this");
    });
  });

  // ==============================================================
  // Profile CRUD: setProfile, getProfilesByProfiler, hasProfile
  // ==============================================================

  describe("Profile CRUD", function () {
    beforeEach(async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
    });

    it("setProfile creates a profile", async function () {
      const profile = makeSoul("degree_CS", "ipfs://cert1", 90);
      await expect(sbt.setProfile(profiler1.address, soul1.address, profile, profiler1.address))
        .to.emit(sbt, "SetProfile").withArgs(profiler1.address, soul1.address, "degree_CS");

      expect(await sbt.hasProfile(profiler1.address, soul1.address)).to.be.true;
      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(1);
      expect(profiles[0].identity).to.equal("degree_CS");
    });

    it("setProfile rejects profile for unminted soul", async function () {
      await expect(sbt.setProfile(profiler1.address, soul2.address, makeSoul("cert", "url"), profiler1.address))
        .to.be.revertedWith("Cannot create a profile for a soul that has not been minted");
    });

    it("setProfile updates existing profile with same identity", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url_v1", 80), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url_v2", 95), profiler1.address);

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(1);
      expect(profiles[0].url).to.equal("url_v2");
      expect(profiles[0].score).to.equal(95);
    });

    it("multiple profiles from same profiler", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url1"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert2", "url2"), profiler1.address);

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(2);
    });

    it("hasProfile returns false when no profiles", async function () {
      expect(await sbt.hasProfile(profiler1.address, soul1.address)).to.be.false;
    });
  });

  // ==============================================================
  // removeProfile: swap-and-pop correctness
  // ==============================================================

  describe("removeProfile", function () {
    beforeEach(async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
    });

    it("removes a single profile", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url1"), profiler1.address);
      await expect(sbt.removeProfile(profiler1.address, soul1.address, "cert1", profiler1.address))
        .to.emit(sbt, "RemoveProfile");

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(0);
    });

    it("removes from single-element array (no swap needed)", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("only", "url"), profiler1.address);
      await sbt.removeProfile(profiler1.address, soul1.address, "only", profiler1.address);

      expect(await sbt.hasProfile(profiler1.address, soul1.address)).to.be.false;
    });

    it("swap-and-pop preserves remaining profiles", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("A", "urlA"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("B", "urlB"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("C", "urlC"), profiler1.address);

      // Remove A (first element) — B or C swapped into position 0
      await sbt.removeProfile(profiler1.address, soul1.address, "A", profiler1.address);

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(2);
      const identities = profiles.map(p => p.identity).sort();
      expect(identities).to.deep.equal(["B", "C"]);
    });

    it("remove middle element preserves others", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("A", "urlA"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("B", "urlB"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("C", "urlC"), profiler1.address);

      await sbt.removeProfile(profiler1.address, soul1.address, "B", profiler1.address);

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(2);
      const identities = profiles.map(p => p.identity).sort();
      expect(identities).to.deep.equal(["A", "C"]);
    });

    it("remove last element (no swap needed)", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("A", "urlA"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("B", "urlB"), profiler1.address);

      await sbt.removeProfile(profiler1.address, soul1.address, "B", profiler1.address);

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(1);
      expect(profiles[0].identity).to.equal("A");
    });

    it("rejects removal of nonexistent profile", async function () {
      await expect(sbt.removeProfile(profiler1.address, soul1.address, "ghost", profiler1.address))
        .to.be.revertedWith("Profile with that identity not found");
    });

    it("profiler can remove own profile", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert", "url"), profiler1.address);
      await sbt.removeProfile(profiler1.address, soul1.address, "cert", profiler1.address);
      expect(await sbt.hasProfile(profiler1.address, soul1.address)).to.be.false;
    });

    it("soul owner can remove any profiler's profile", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert", "url"), profiler1.address);
      await sbt.removeProfile(profiler1.address, soul1.address, "cert", soul1.address);
      expect(await sbt.hasProfile(profiler1.address, soul1.address)).to.be.false;
    });

    it("stranger cannot remove profile", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert", "url"), profiler1.address);
      await expect(sbt.removeProfile(profiler1.address, soul1.address, "cert", stranger.address))
        .to.be.revertedWith("Only profiler or soul can remove profile");
    });

    it("index is correct after remove-then-add", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("A", "urlA"), profiler1.address);
      await sbt.removeProfile(profiler1.address, soul1.address, "A", profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("A", "urlA_v2"), profiler1.address);

      const profiles = await sbt.getProfilesByProfiler(profiler1.address, soul1.address);
      expect(profiles.length).to.equal(1);
      expect(profiles[0].url).to.equal("urlA_v2");
    });
  });

  // ==============================================================
  // Revocation
  // ==============================================================

  describe("Revocation", function () {
    beforeEach(async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url1"), profiler1.address);
    });

    it("revokeProfile sets isRevoked to true", async function () {
      await sbt.revokeProfile(profiler1.address, soul1.address, "cert1", profiler1.address);
      expect(await sbt.checkRevocation(profiler1.address, soul1.address, "cert1")).to.be.true;
    });

    it("checkRevocation returns false for non-revoked profile", async function () {
      expect(await sbt.checkRevocation(profiler1.address, soul1.address, "cert1")).to.be.false;
    });

    it("checkRevocation returns false for nonexistent profile", async function () {
      expect(await sbt.checkRevocation(profiler1.address, soul1.address, "ghost")).to.be.false;
    });

    it("revoke rejects nonexistent profile", async function () {
      await expect(sbt.revokeProfile(profiler1.address, soul1.address, "ghost", profiler1.address))
        .to.be.revertedWith("Profile with that identity not found");
    });

    it("soul owner can revoke", async function () {
      await sbt.revokeProfile(profiler1.address, soul1.address, "cert1", soul1.address);
      expect(await sbt.checkRevocation(profiler1.address, soul1.address, "cert1")).to.be.true;
    });

    it("stranger cannot revoke", async function () {
      await expect(sbt.revokeProfile(profiler1.address, soul1.address, "cert1", stranger.address))
        .to.be.revertedWith("Only profiler or soul can revoke profile");
    });
  });

  // ==============================================================
  // listProfiles: paired arrays, multi-profiler
  // ==============================================================

  describe("listProfiles", function () {
    beforeEach(async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
    });

    it("returns empty arrays for soul with no profiles", async function () {
      const [souls, issuers] = await sbt.listProfiles(soul1.address);
      expect(souls.length).to.equal(0);
      expect(issuers.length).to.equal(0);
    });

    it("returns paired arrays of equal length", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url1"), profiler1.address);
      await sbt.setProfile(profiler2.address, soul1.address, makeSoul("cert2", "url2"), profiler2.address);

      const [souls, issuers] = await sbt.listProfiles(soul1.address);
      expect(souls.length).to.equal(issuers.length);
      expect(souls.length).to.equal(2);
    });

    it("pairs each soul with correct issuer", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert_from_P1", "url1"), profiler1.address);
      await sbt.setProfile(profiler2.address, soul1.address, makeSoul("cert_from_P2", "url2"), profiler2.address);

      const [souls, issuers] = await sbt.listProfiles(soul1.address);

      for (let i = 0; i < souls.length; i++) {
        if (souls[i].identity === "cert_from_P1") {
          expect(issuers[i]).to.equal(profiler1.address);
        } else {
          expect(issuers[i]).to.equal(profiler2.address);
        }
      }
    });

    it("handles multiple profiles from multiple profilers", async function () {
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("P1_A", "url"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("P1_B", "url"), profiler1.address);
      await sbt.setProfile(profiler2.address, soul1.address, makeSoul("P2_A", "url"), profiler2.address);

      const [souls, issuers] = await sbt.listProfiles(soul1.address);
      expect(souls.length).to.equal(3);
      expect(issuers.length).to.equal(3);
    });
  });

  // ==============================================================
  // burn cleanup: profiles from multiple profilers
  // ==============================================================

  describe("Burn Cleanup", function () {
    it("burn cleans up all profiles from all profilers", async function () {
      await sbt.mint(soul1.address, makeSoul("alice", "url"));
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert1", "url1"), profiler1.address);
      await sbt.setProfile(profiler1.address, soul1.address, makeSoul("cert2", "url2"), profiler1.address);
      await sbt.setProfile(profiler2.address, soul1.address, makeSoul("cert3", "url3"), profiler2.address);

      await sbt.burn(soul1.address, soul1.address);

      expect(await sbt.hasSoul(soul1.address)).to.be.false;
      expect(await sbt.hasProfile(profiler1.address, soul1.address)).to.be.false;
      expect(await sbt.hasProfile(profiler2.address, soul1.address)).to.be.false;

      const [souls, issuers] = await sbt.listProfiles(soul1.address);
      expect(souls.length).to.equal(0);
    });
  });
});
