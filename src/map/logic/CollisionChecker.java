package map.logic;

import map.entity.Entity;

public class CollisionChecker {

    // Reference to the GamePanel, used for accessing game elements like tiles and entities
    GamePanel gp;

    // Constructor that initializes the CollisionChecker with the game panel reference
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Method to check for tile collision for a given entity
    public void checkTile(Entity entity) {
        // Calculate the entity's current position in the world
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate the tile columns and rows based on the entity's position
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        // Check for collisions based on the direction of the entity
        switch (entity.direction) {
            case "up":
                // Check the tiles above the entity
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                // If any tile has collision enabled, set collisionOn to true
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                // Check the tiles below the entity
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                // If any tile has collision enabled, set collisionOn to true
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                // Check the tiles to the left of the entity
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                // If any tile has collision enabled, set collisionOn to true
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                // Check the tiles to the right of the entity
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                // If any tile has collision enabled, set collisionOn to true
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    // Method to check for collisions between an entity and a list of target entities (e.g., NPCs)
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999; // Default index value indicating no collision

        // Loop through all target entities to check for collisions
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Update the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Update the target entity's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                // Check collision based on the entity's direction
                switch (entity.direction) {
                    case "up":
                        // Move the entity's solid area up and check for intersection with the target
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i; // Set index to the target entity's index
                        }
                        break;

                    case "down":
                        // Move the entity's solid area down and check for intersection with the target
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i; // Set index to the target entity's index
                        }
                        break;

                    case "left":
                        // Move the entity's solid area left and check for intersection with the target
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i; // Set index to the target entity's index
                        }
                        break;

                    case "right":
                        // Move the entity's solid area right and check for intersection with the target
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i; // Set index to the target entity's index
                        }
                        break;
                }

                // Reset the positions of the solid areas to their default values
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index; // Return the index of the colliding entity, or 999 if none
    }
}