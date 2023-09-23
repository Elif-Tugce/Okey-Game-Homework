import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already sorted
     */
    public void distributeTilesToPlayers() {
        int playerIndex = getCurrentPlayerIndex();

    }

    /*
     * TODO (DONE): get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[getCurrentPlayerIndex()].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {

        return null;
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {

    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. Use calculateLongestChainPerTile method to get the
     * longest chains per tile.
     * To win, you need one of the following cases to be true:
     * - 8 tiles have length >= 4 and remaining six tiles have length >= 3 the last one can be of any length
     * - 5 tiles have length >= 5 and remaining nine tiles have length >= 3 the last one can be of any length
     * These are assuming we check for the win condition before discarding a tile
     * The given cases do not cover all the winning hands based on the original
     * game and for some rare cases it may be erroneous but it will be enough
     * for this simplified version
     */
    public boolean didGameFinish() {
        return false;
    }

    /*
     * TODO (DONE): Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You may choose randomly or consider if the discarded tile is useful for
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        boolean pickedLastDiscarded = false;
        for (int i = 0; i < players[currentPlayerIndex].playerTiles.length; i++){
            if (players[getCurrentPlayerIndex()].playerTiles[i].canFormChainWith(lastDiscardedTile) != 0){
                pickedLastDiscarded = true;
                getLastDiscardedTile();
                System.out.println("The computer picked the last discarded tile");
            }

        }
            if(!pickedLastDiscarded){
                getTopTile();
                System.out.println("The computer picked a tile from the tiles array");
            }
    }

    /*
     * TODO (DONE): Current computer player will discard the least useful tile.
     * For this use the findLongestChainOf method in Player class to calculate
     * the longest chain length per tile of this player,
     * then choose the tile with the lowest chain length and discard it
     * this method should print what tile is discarded since it should be
     * known by other players
     */
    public void discardTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        int[] chainLengths = new int[currentPlayer.playerTiles.length];
        int indexOfTileToDiscard = 0;
        int index = 0;

        for (int i = 0; i < currentPlayer.playerTiles.length; i++){
            chainLengths[i] = currentPlayer.findLongestChainOf(currentPlayer.playerTiles[i]);
        }

        for (int i = 0; i < chainLengths.length; i ++){
            if (chainLengths[i] < chainLengths[i + 1]){
                indexOfTileToDiscard = i;
            }
        }

        lastDiscardedTile = currentPlayer.playerTiles[indexOfTileToDiscard];
        System.out.printf("The computer has discarded the tile %d", lastDiscardedTile);

        while(index < currentPlayer.playerTiles.length){
            currentPlayer.playerTiles[indexOfTileToDiscard] = currentPlayer.playerTiles[indexOfTileToDiscard + 1];
            index++;
        }

    }

    /*
     * TODO (DONE): discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[getCurrentPlayerIndex()].getAndRemoveTile(tileIndex);
    }

    public void currentPlayerSortTilesColorFirst() {
        players[currentPlayerIndex].sortTilesColorFirst();
    }

    public void currentPlayerSortTilesValueFirst() {
        players[currentPlayerIndex].sortTilesValueFirst();
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
