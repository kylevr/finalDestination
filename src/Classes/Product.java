package Classes;

import java.util.Random;

public class Product {

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
    
    public String getGTIN(){
        return this.GTIN;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public CategoryEnum getCategory(){
        return this.category;
    }
    
    public int getId() {
        return id;
    }
    
    public void randomEnum(){
        int pick = new Random().nextInt(CategoryEnum.values().length);
        if (this.name.contains("Volvo")){
            this.category = CategoryEnum.Vehicles;
        }
        else{
        this.category = CategoryEnum.values()[pick];
        }           
    }
}
