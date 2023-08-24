package comp1110.ass2.model;

public class Rug {
    private String colors;
    private Player owner;




    public Rug(String colors, Player owner){
//        this.colors = colors;
//        this.owner = owner;

    }

    /**
     * Get the rug colour in select tile .
     */
    public String getColors(){
        return colors;
    }

    /**
     * Get the rug owner in select tile .
     */
    public Player getOwner(){
        return owner;
    }


}
