#!/usr/bin/env python3
"""Validate mod resources - fabric.mod.json, mixins, data/assets structure."""

import json
import os
import sys
from pathlib import Path


def check_fabric_mod_json():
    path = Path("src/main/resources/fabric.mod.json")
    if not path.exists():
        print("ERROR: fabric.mod.json not found")
        return False

    with open(path) as f:
        data = json.load(f)

    required = ["id", "version", "name", "entrypoints", "depends"]
    for key in required:
        if key not in data:
            print(f"ERROR: fabric.mod.json missing required key: {key}")
            return False

    if data["id"] != "uncannyvoxel":
        print(f"ERROR: mod id mismatch: {data['id']} != uncannyvoxel")
        return False

    entrypoints = data.get("entrypoints", {})
    if "main" not in entrypoints or "client" not in entrypoints:
        print("ERROR: missing main or client entrypoints")
        return False

    print("OK: fabric.mod.json valid")
    return True


def check_mixins_json():
    path = Path("src/main/resources/uncannyvoxel.mixins.json")
    if not path.exists():
        print("ERROR: uncannyvoxel.mixins.json not found")
        return False

    with open(path) as f:
        data = json.load(f)

    if data.get("injectors", {}).get("defaultRequire") != 0:
        print("ERROR: defaultRequire must be 0 for 26.2 unverified targets")
        return False

    client_mixins = data.get("client", [])
    required_client = [
        "client.VulkanLightmapDropMixin",
        "client.VulkanChunkDrawMixin",
        "client.FriendListScreenMixin",
        "client.FriendEntryMixin",
        "client.PlayerListHudMixin",
        "client.WorldRendererMixin",
        "client.GameRendererMixin",
        "client.CameraMixin",
        "client.HandledScreenMixin"
    ]

    for mixin in required_client:
        if mixin not in client_mixins:
            print(f"ERROR: missing required client mixin: {mixin}")
            return False

    print("OK: uncannyvoxel.mixins.json valid")
    return True


def check_data_structure():
    required_dirs = [
        "data/uncannyvoxel/tags/block",
        "data/uncannyvoxel/dimension",
        "data/uncannyvoxel/dimension_type",
        "data/uncannyvoxel/worldgen/biome",
        "data/uncannyvoxel/worldgen/structure",
        "data/uncannyvoxel/worldgen/structure_set",
    ]

    for d in required_dirs:
        if not Path(f"src/main/resources/{d}").exists():
            print(f"ERROR: missing data directory: {d}")
            return False

    required_files = [
        "data/uncannyvoxel/tags/block/substrate_frame.json",
        "data/uncannyvoxel/tags/block/sulfur_blocks.json",
        "data/uncannyvoxel/dimension/substrate.json",
        "data/uncannyvoxel/dimension_type/substrate_type.json",
        "data/uncannyvoxel/worldgen/biome/substrate_hall.json",
        "data/uncannyvoxel/worldgen/structure/flesh_farm.json",
        "data/uncannyvoxel/worldgen/structure/weeping_monolith.json",
    ]

    for f in required_files:
        if not Path(f"src/main/resources/{f}").exists():
            print(f"ERROR: missing required file: {f}")
            return False

    print("OK: data structure valid")
    return True


def check_assets_structure():
    required_dirs = [
        "assets/uncannyvoxel/lang",
        "assets/uncannyvoxel/blockstates",
        "assets/uncannyvoxel/models/block",
        "assets/uncannyvoxel/models/item",
        "assets/uncannyvoxel/textures/block",
        "assets/uncannyvoxel/textures/entity",
    ]

    for d in required_dirs:
        if not Path(f"src/main/resources/{d}").exists():
            print(f"ERROR: missing assets directory: {d}")
            return False

    required_files = [
        "assets/uncannyvoxel/lang/en_us.json",
        "assets/uncannyvoxel/sounds.json",
    ]

    for f in required_files:
        if not Path(f"src/main/resources/{f}").exists():
            print(f"ERROR: missing required file: {f}")
            return False

    print("OK: assets structure valid")
    return True


def main():
    checks = [
        check_fabric_mod_json,
        check_mixins_json,
        check_data_structure,
        check_assets_structure,
    ]

    all_passed = True
    for check in checks:
        if not check():
            all_passed = False

    if all_passed:
        print("\nAll resource validation checks passed!")
        return 0
    else:
        print("\nSome validation checks failed!")
        return 1


if __name__ == "__main__":
    sys.exit(main())