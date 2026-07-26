package com.uncannyvoxel.ci;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class ValidateResources {
    public static void main(String[] args) throws IOException {
        Path resourcesDir = Path.of("src/main/resources");
        if (!Files.exists(resourcesDir)) {
            System.err.println("Resources directory not found");
            System.exit(1);
        }

        int errors = 0;

        Path fabricModJson = resourcesDir.resolve("fabric.mod.json");
        if (!Files.exists(fabricModJson)) {
            System.err.println("Missing fabric.mod.json");
            errors++;
        } else {
            String content = Files.readString(fabricModJson);
            if (!content.contains("\"id\": \"uncannyvoxel\"")) {
                System.err.println("fabric.mod.json missing correct mod ID");
                errors++;
            }
        }

        Path mixinsJson = resourcesDir.resolve("uncannyvoxel.mixins.json");
        if (!Files.exists(mixinsJson)) {
            System.err.println("Missing uncannyvoxel.mixins.json");
            errors++;
        }

        checkDir(resourcesDir.resolve("data/uncannyvoxel/tags/block"), "substrate_frame.json", "sulfur_blocks.json");
        checkDir(resourcesDir.resolve("data/uncannyvoxel/dimension"), "substrate.json");
        checkDir(resourcesDir.resolve("data/uncannyvoxel/dimension_type"), "substrate_type.json");
        checkDir(resourcesDir.resolve("data/uncannyvoxel/worldgen/biome"), "substrate_hall.json");
        checkDir(resourcesDir.resolve("data/uncannyvoxel/worldgen/structure"), "flesh_farm.json", "weeping_monolith.json");
        checkDir(resourcesDir.resolve("assets/uncannyvoxel/lang"), "en_us.json");
        checkDir(resourcesDir.resolve("assets/uncannyvoxel/blockstates"), "sulfur_glass_mirror.json", "rusted_grate.json", "compacted_hair.json", "chest_mimic.json", "tether_stake.json");
        checkDir(resourcesDir.resolve("assets/uncannyvoxel/models/block"), "sulfur_glass_mirror.json", "rusted_grate.json", "compacted_hair.json", "chest_mimic.json", "tether_stake.json");
        checkDir(resourcesDir.resolve("assets/uncannyvoxel/models/item"), "desaturated_eye.json", "lumen_scalpel.json", "tether_stake.json");
        checkDir(resourcesDir.resolve("assets/uncannyvoxel/sounds.json"));

        if (errors > 0) {
            System.err.println("Resource validation failed with " + errors + " errors");
            System.exit(1);
        }

        System.out.println("All resources validated successfully");
    }

    private static void checkDir(Path dir, String... requiredFiles) throws IOException {
        if (!Files.exists(dir)) {
            System.err.println("Missing directory: " + dir);
            return;
        }

        Set<String> existingFiles = Files.list(dir)
                .map(p -> p.getFileName().toString())
                .collect(java.util.stream.Collectors.toSet());

        for (String file : requiredFiles) {
            if (!existingFiles.contains(file)) {
                System.err.println("Missing file: " + dir + "/" + file);
            }
        }
    }
}
