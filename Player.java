public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * This method calculates the longest chain per tile to be used when checking the win condition
     */
    public int[] calculateLongestChainPerTile() {
        // keep a seperate copy of the tiles since findLongestChainOf sorts them
        Tile[] tilesCopy = new Tile[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            tilesCopy[i] = playerTiles[i];
        }

        // make the calculations
        int[] chainLengths = new int[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            chainLengths[i] = findLongestChainOf(tilesCopy[i]);
        }

        // revert the playerTiles to its original form
        for (int i = 0; i < numberOfTiles; i++) {
            playerTiles[i] = tilesCopy[i];
        }

        return chainLengths;
    }

    /*
     * TODO: finds and returns the longest chain of tiles that can be formed
     * using the given tile. a chain of tiles is either consecutive numbers
     * that have the same color or the same number with different colors
     * some chain examples are as follows:
     * 1B 2B 3B
     * 5Y 5B 5R 5K
     * 4Y 5Y 6Y 7Y 8Y
     * You can use canFormChainWith method in Tile class to check if two tiles can make a chain
     * based on color order and value order. Use sortTilesColorFirst() and sortTilesValueFirst()
     * methods to sort the tiles of this player then find the position of the given tile t.
     * check how many adjacent tiles there are starting from the tile poisition.
     * Note that if you start a chain with matching colors it should continue with the same type of match
     * and if you start a chain with matching values it should continue with the same type of match
     * use the different values canFormChainWith method returns.
     */
    public int findLongestChainOf(Tile t) {
        int tilePosition;

        sortTilesColorFirst();
        tilePosition = findPositionOfTile(t);

        // TODO: find the longest chain starting from tilePosition going left and right
        int longestChainColorFirst = 0;


        int i = tilePosition;

        while( i < numberOfTiles -1 &&  i > -1 && playerTiles[i].canFormChainWith(playerTiles[i+1]) == 1 ){

            longestChainColorFirst++;
            i++;

        }

        i = tilePosition;

        while (i < numberOfTiles-1 &&  i > 0 && playerTiles[i].canFormChainWith(playerTiles[i-1]) == 1 ){
            longestChainColorFirst++;
            i--;

        }
        int longestChainValueFirst = 0;

        sortTilesValueFirst();
        tilePosition = findPositionOfTile(t);

        i = tilePosition;

         
        while(i < numberOfTiles-1 && i > -1 && playerTiles[i].canFormChainWith(playerTiles[i+1]) == 2  ){

            longestChainValueFirst++;
            i++;

        }

        i = tilePosition;


        while (i < numberOfTiles-1  &&  i >0 && playerTiles[i].canFormChainWith(playerTiles[i-1]) == 2){
            longestChainValueFirst++;
            i--;

        }

               
        
        // TODO: find the longest chain starting from tilePosition going left and right
        



        if(longestChainColorFirst > longestChainValueFirst) {
            return longestChainColorFirst;
        }
        else{
            return longestChainValueFirst;
        }
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        if (index < 0 || index >= numberOfTiles) {
            return null; // Invalid index
        }

        Tile removedTile = playerTiles[index];

        // Shift tiles to fill the gap
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];
        }

        playerTiles[numberOfTiles - 1] = null; // Clear the last element
        numberOfTiles--;

        return removedTile;
    }

    /*
     * TODO(DONE): adds the given tile at the end of playerTiles array, should also
     * update numberOfTiles accordingly. Make sure the player does not try to
     * have more than 15 tiles at a time
     */
   public void addTile(Tile t) {
        if (numberOfTiles < 15) {
            playerTiles[numberOfTiles] = t;
            numberOfTiles++;
        } else if (numberOfTiles > 15) {
            System.out.println("Maximum tile limit reached. Cannot add more tiles.");
        }
    }

    /*
     * TODO(DONE): uses bubble sort to sort playerTiles in increasing color and value
     * value order: 1 < 2 < ... < 12 < 13
     * color order: Y < B < R < K
     * color is more important in this ordering, a sorted example:
     * 3Y 3Y 6Y 7Y 1B 2B 3B 3B 10R 11R 12R 2K 4K 5K
     * you can use compareToColorFirst method in Tile class for comparing
     * you are allowed to use Collections.sort method
     */
    public void sortTilesColorFirst() {
        Tile temp = new Tile(1, 'Y');
        for (int i = 0; i < numberOfTiles - 1; i++) {
            for (int j = 0; j < numberOfTiles - 1 - i; j++) {
                if (playerTiles[j].compareToColorFirst(playerTiles[j+1]) == 1) {
                    temp = playerTiles[j];
                    playerTiles[j] = playerTiles[j+1];
                    playerTiles[j+1] = temp;
                }
            }
        }
    }

    /*
     * TODO(DONE): uses bubble sort to sort playerTiles in increasing value and color
     * value order: 1 < 2 < ... < 12 < 13
     * color order: Y < B < R < K
     * value is more important in this ordering, a sorted example:
     * 1B 2B 2K 3Y 3Y 3B 3B 4K 5K 6Y 7Y 10R 11R 12R
     * you can use compareToValueFirst method in Tile class for comparing
     * you are allowed to use Collections.sort method
     */
    public void sortTilesValueFirst() {
        Tile temp = new Tile(1, 'Y');
        for (int i = 0; i < numberOfTiles - 1; i++) {
            for (int j = 0; j < numberOfTiles - 1 - i; j++) {
                if (playerTiles[j].compareToValueFirst(playerTiles[j+1]) == 1) {
                    temp = playerTiles[j];
                    playerTiles[j] = playerTiles[j+1];
                    playerTiles[j+1] = temp;
                }
            }
        }
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
