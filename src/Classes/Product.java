package Classes;

import java.io.Serializable;
import java.util.Random;

public class Product implements Serializable {

    private int id;


    private String GTIN;
    private String name;
    private String description;
    private CategoryEnum category;

    
    public Product(int id, String GTIN, String name, String description) {
        this.id = id;
        this.GTIN = GTIN;
        this.name = name.toLowerCase();
        this.description = description;
        randomEnum();
    }
    /**
     * returns Gtin of product to be sold
     * @return string
     */
    public String getGTIN(){
        return this.GTIN;
    }
    /**
     * returns name of product
     * @return string
     */
    public String getName(){
        return this.name;
    }
    /**
     * returns description of product
     * @return string
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * returns category of product
     * @return CategoryEnum
     */
    public CategoryEnum getCategory(){
        return this.category;
    }
    /**
     * returns id of product
     * @return 
     */
    public int getId() {
        return id;
    }
    /**
     * sets random category, for testing purpose
     */
    public void randomEnum(){
        int pick = new Random().nextInt(CategoryEnum.values().length);
        if (this.name.contains("Volvo")){
            this.category = CategoryEnum.Vehicles;
        }
        else{
        this.category = CategoryEnum.Electronics;
        }           
    }
}
