package com.example.supermarketapp.dataentities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Product::class, ShoppingListItem::class, WishlistItem::class, PurchaseHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ProductDao(): ProductDao
    abstract fun ShoppingListDao(): ShoppingListDao
    abstract fun WishlistDao(): WishlistDao
    abstract fun PurchaseHistoryDao(): PurchaseHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .addCallback(SeedDatabaseCallback(context.applicationContext))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedDatabaseCallback(
        private val context: Context
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).ProductDao()

                //Fruits
                dao.insert(Product(null, "Apples", "Fruits", "Fresh organic Apples. Nutritional value (per 100g): 52 kcal, 0.3g protein, 0.2g fat, 14g carbohydrates. Price/kg = 4€", 2.0, 50, "apples", "kg", false, null, false, 0.0, 2.0))
                dao.insert(Product(null, "Banana", "Fruits", "Fresh Bananas from Tunisia. Nutritional value (per 100g): 89 kcal, 1.1g protein, 0.3g fat, 23g carbohydrates. 5 Bananas = 1 kg. Price/kg = 1.5€", 1.5, 80, "banana", "kg", false, null, false, 0.0, 1.5))
                dao.insert(Product(null, "Mango", "Fruits", "Fresh mango, from Latin America.Nutritional value (per 100g): 60 kcal, 0.8g protein, 0.4g fat, 15g carbohydrates. 3 Mangos = 1 kg. Price/kg = 7.5€", 2.5, 10, "mango", "pc", false, null, true, 0.2, 2.5-0.2*2.5))
                dao.insert(Product(null, "Cherries", "Fruits", "Fresh cherries without seed (packaged). Weight: 250 gr. Nutritional value (per 100g): 63 kcal, 1g protein, 0.2g fat, 16g carbohydrates. Each packet weighs 250gr.  Price/kg = 12€.", 3.0, 25, "cherries", "pcs", true, "1+1", false, 0.0, 3.0))
                dao.insert(Product(null, "Watermelon", "Fruits", "Fresh watermelon from Greek farmers. Nutritional value (per 100g): 30 kcal, 0.6g protein, 0.2g fat, 8g carbohydrates. A watermelon weighs around 7-8. Price/kg = 1€", 7.0, 10, "watermelon", "kg", false, null, false, 0.0, 7.0))
                dao.insert(Product(null, "Blueberries", "Fruits", "Fresh Organic Blueberries. Nutritional value (per 100g): 57 kcal, 0.7g protein, 0.3g fat, 14g carbohydrates. Each pack contains 250gr. Price/kg = 5€", 1.25, 8, "blueberries", "pcs", false, null, true, 0.2, 1.25-0.2*1.25))
                //Vegetables
                dao.insert(Product(null, "Tomatoes", "Vegetables", "Greenhouse tomatoes, rich in lycopene.Nutritional value (per 100g): 18 kcal, 0.9g protein, 0.2g fat, 3.9g carbohydrates. Price/kg1.1€", 1.1, 40, "tomatoes", "kg", false, null, false, 0.0, 1.1))
                dao.insert(Product(null, "Potatoes", "Vegetables", "Potatoes from Egypt. Nutritional value (per 100g): 77 kcal, 2g protein, 0.1g fat, 17g carbohydrates. Price/kg = 1.8€", 1.8, 50, "potatoes", "kg", false, null, true,0.2, 1.8-0.2*1.8))
                dao.insert(Product(null, "Cucumbers", "Vegetables", "Fresh greek cucumbers. Nutritional value (per 100g): 16 kcal, 0.7g protein, 0.1g fat, 3.6g carbohydrates. Price/kg = 1.2€", 1.2, 20, "cucumbers", "kg", false, null, false, 0.0, 1.2))
                dao.insert(Product(null, "Lettuce", "Vegetables", "Fresh lettuce, washed and ready to use. Nutritional value (per 100g): 15 kcal, 1.4g protein, 0.2g fat, 2.9g carbohydrates. Price/kg = 3€", 1.50, 30, "lettuce", "kg", false, null, false, 0.0, 1.5))
                //Bakery
                dao.insert(Product(null, "Bread", "Bakery", "Wholegrain bread. Ingredients: Wheat flour, yeast, salt, water. Nutritional value (per 100g): 265 kcal, 9g protein, 3.2g fat, 49g carbohydrates. One loaf of bread weighs 1 kg", 1.0, 22, "bread","pcs", false, null, false, 0.0, 1.0))
                dao.insert(Product(null, "Wheat Crackers", "Bakery", "Wholegrain crackers. Ingredients: Whole wheat flour, olive oil, salt. Nutritional value (per 100g): 430 kcal, 10g protein, 16g fat, 60g carbohydrates. Each bag weighs 200gr.", 2.3, 10, "wheat_crackers", "pcs", false, null, true, 0.3, 2.3-0.3*2.3))
                //Dairy
                dao.insert(Product(null, "Yogurt", "Dairy", "Yogurt from whole cow milk. Nutritional value (per 100g): 61 kcal, 3.5g protein, 3.3g fat, 4.7g carbohydrates. Each cup weighs 200kg. Price/kg = 9€.", 1.8, 40, "yogurt", "pcs", true, "1+1", false, 0.0, 1.2))
                dao.insert(Product(null, "Milk", "Dairy", "Pasteurized whole cow's milk. Nutritional value (per 100ml): 64 kcal, 3.3g protein, 3.7g fat, 4.8g carbohydrates. Each bottle contains 1.5L. Price/L = 2€", 3.0, 30, "milk", "pcs", false, null, true, 0.0, 3.0-0.3*3.0))
                dao.insert(Product(null, "Feta Cheese", "Dairy", "Feta from Greece. Ingredients: Sheep & goat milk, salt, rennet. Nutritional value (per 100g): 264 kcal, 14g protein, 21g fat, 4g carbohydrates. Each pack weighs 500gr. 1 kg costs 8€", 4.0, 25, "feta", "kg", false, null, true, 0.1, 4.0-0.1*4.0))
                dao.insert(Product(null, "Gouda", "Dairy", "Danish gouda cheese. Ingredients: Whole cow's milk, salt, ripening cultures. Nutritional value (per 100g): 356 kcal, 25g protein, 27g fat, 2g carbohydrates. Each bag weighs 250 kg. Price/kg = 10€", 2.5, 20, "gouda", "pcs", false, null, false, 0.0, 2.5))
                //Meat
                dao.insert(Product(null, "Beef", "Meat", "Fresh Beef. Nutritional value (per 100g): 250 kcal, 26g protein, 17g fat. Each pack weighs 500gr. Price/kg = 9.8€.", 4.9, 8, "beef", "pcs", false, null, false, 0.0, 1.2))
                dao.insert(Product(null, "Pork", "Meat", "Boneless pork, suitable for cooking or grilling. Nutritional value (per 100g): 242 kcal, 22g protein, 17g fat. Each pack weighs 500gr. Price/kg = 6.6€.", 3.3, 16, "pork", "pcs", false, null, false, 0.0, 1.2))
                dao.insert(Product(null, "Chicken", "Meat", "Fresh chicken breasts, without antibiotics. Nutritional value (per 100g): 165 kcal, 31g protein, 3.6g fat. Each pack weighs 500gr. Price/kg = 5.6€.", 2.8, 30, "chicken", "pcs", false, null, true, 0.3, 2.8-0.3*2.8))
                dao.insert(Product(null, "Chicken Nuggets", "Meat", "Crunchy chicken nuggets from chicken creast. Ingredients: Shredded chicken fillet, breadcrumbs, vegetable oil, spices. Nutritional value (per 100g): 270 kcal, 14g protein, 18g fat, 14g carbohydrates. Each pack weighs 500gr. Price/kg = 7€", 3.5, 25, "chicken_nuggets", "pcs", true, "1+1", false, 0.0, 3.5))
                //Breakfast
                dao.insert(Product(null, "Cereal", "Breakfast", "Cheerios cereal, suitable for a full breakfast. Ingredients: Whole grains, sugar, vitamins. Nutritional value (per 100g): 370 kcal, 7g protein, 4g fat, 80g carbohydrates. Each pack weighs 500gr. Price/kg = 5.8€", 2.9, 20, "cereal", "pcs", false, null, true, 0.4, 2.9-0.4*2.9))
                dao.insert(Product(null, "Coffee", "Breakfast", "100% Arabica coffee. Nutritional value: 0 kcal (without additives). Each pack weighs 250gr. Price/kg = 11.2€", 2.8, 10, "coffee", "pcs", false, null, false, 0.0, 2.8))
                dao.insert(Product(null, "Marmalade", "Breakfast", "Marmalade from 100% fruits. Nutritional value (per 100g): 250 kcal, 0.5g protein, 0.2g fat, 60g carbohydrates. Each jar weighs 330gr. Price/kg = 10.5€", 3.5, 20, "marmalade", "pcs", false, null, true, 0.3, 3.5-0.3*3.5))
                //Frozen
                dao.insert(Product(null, "Peas", "Frozen", "Frozen peas, ready to cook. Nutritional value (per 100g): 81 kcal, 5g protein, 0.4g fat, 14g carbohydrates. Each bag weighs 1kg. Price/kg = 3.5€", 3.5, 25, "peas", "pcs", false, null, false, 0.0, 3.5))
                dao.insert(Product(null, "Ice Cream", "Frozen", "Ingredients: Whole milk, sugar, egg, natural flavors (vanilla/chocolate). Nutritional value (per 100g): 207 kcal, 3.5g protein, 11g fat, 24g carbohydrates. Each pack weighs 500gr. Price/kg = 8.6€", 3.8, 10, "ice cream","pcs", true, "1+1", false, 0.0, 3.8))
                //Grocery
                dao.insert(Product(null, "Spaghetti", "Grocery", "Spaghetti no.6. Ingredients: Wheat semolina, water. Nutritional value (per 100g): 371 kcal, 13g protein, 1.5g fat, 75g carbohydrates. Each pack weighs 500gr. Price/kg = 2.8€", 1.4, 50, "spaghetti", "pcs", true, "1+1", false, 0.0, 1.4))
                dao.insert(Product(null, "Rice", "Grocery", "Long grain basmati rice. Nutritional value (per 100g): 365 kcal, 7g protein, 0.7g fat, 80g carbohydrates. Each pack weighs 500gr. Price/kg = 4.6€", 2.3, 40, "rice", "pcs", false, null, false, 0.0, 2.3))
                dao.insert(Product(null, "Beans", "Grocery", "Dried medium beans. Nutritional value (per 100g): 347 kcal, 21g protein, 1.5g fat, 60g carbohydrates. Each pack weighs 500gr. Price/kg = 7€", 3.5, 30, "beans", "pcs", false, null, true, 0.4, 3.5-0.4*3.5))
                dao.insert(Product(null, "Lentil", "Grocery", "Thin lentils. Nutritional value (per 100g): 353 kcal, 25g protein, 1g fat, 60g carbohydrates. Each pack weighs 500gr. Price/kg = 5.6€", 2.8, 30, "lentil", "pcs", false, null, false, 0.0, 2.8))
                dao.insert(Product(null, "Chickpeas", "Grocery", "Dried chickpeas. Nutritional value (per 100g): 364 kcal, 19g protein, 6g fat, 61g carbohydrates. Each pack weighs 500gr. Price/kg = 5.4€", 2.7, 30, "chickpeas", "pcs", false, null, false, 0.0, 2.7))
                //Snacks
                dao.insert(Product(null, "Chips", "Snacks", "Ingredients: Potatoes, vegetable oil, salt. Nutritional value (per 100g): 530 kcal, 6g protein, 33g fat, 50g carbohydrates. Each bag weighs 150gr. Price/kg = 13.5€", 2.2, 10, "chips", "pcs", false, null, false, 0.0, 2.2))
                dao.insert(Product(null, "Popcorn", "Snacks", "Ingredients: Corn, vegetable oil, salt or butter. Nutritional value (per 100g): 100 kcal, 8g protein, 18g fat, 60g carbohydrates. Each bag weighs 150gr. Price/kg = 12€", 1.9, 20, "popcorn", "pcs", false, null, false, 0.0, 1.9))
                dao.insert(Product(null, "Candy", "Snacks", "Ingredients: Sugar, glucose, flavors, colors. Nutritional value (per 100g): 380 kcal, 0g protein, 0g fat, 95g carbohydrates. Each bag weighs 100gr. Price/kg = 9€.", 1.8, 20, "candy", "pcs", false, null, false, 0.0, 1.8))
                dao.insert(Product(null, "Chocolate", "Snacks", "Ingredients: Cocoa, sugar, cocoa butter, milk powder. Nutritional value (per 100g): 540 kcal, 7g protein, 32g fat, 56g carbohydrates. Each chocolate bar weighs 100gr. Price/kg = 18€.", 1.8, 10, "chocolate", "pcs", false, null, false, 0.0, 1.8))
                dao.insert(Product(null, "Cookies", "Snacks", "Ingredients: Flour, butter, sugar, egg. Nutritional value (per 100g): 480 kcal, 6g protein, 24g fat, 60g carbohydrates. Each packet weighs 200gr. Price/kg = 11€", 2.2, 15, "cookies", "pcs", false, null, false, 0.0, 2.2))
                dao.insert(Product(null, "Croissant", "Snacks", "Ingredients: Flour, butter, egg, sugar, yeast, chocolate. Nutritional value (per 100g): 406 kcal, 8g protein, 21g fat, 45g carbohydrates. Each croissant weighs 80gr. Price/kg = 16.25", 1.3, 10, "croissant", "pcs", true, "1+1", false, 0.0, 1.3))
                //Drinks
                dao.insert(Product(null, "Gin", "Drinks", "London dry gin. Ingredients: Grain distillate, juniper, herbal aromas. Alcohol: 40%. Each bottle contains 700ml Price/L = 20€", 13.9, 10, "gin", "pcs", false, null, false, 0.0, 13.9))
                dao.insert(Product(null, "Beer", "Drinks", "Wheat beer. Ingredients: Water, malt, hops, yeast. Nutritional value (per 330ml): 150 kcal. Price/L = 4.5€", 1.5, 40, "beer", "pcs", true, "1+1", false, 0.0, 1.5))
                dao.insert(Product(null, "Soda", "Drinks", "Ingredients: Carbonated water, citric acid, sweeteners. Nutritional value (per 330ml): 0 kcal (zero). Price/L = 2.7€", 0.9, 100, "soda", "pcs", false, null, false, 0.0, 0.9))
                dao.insert(Product(null, "Orange Juice", "Drinks", "100% Orange Juice. 1.5 litre bottle. Nutritional value (per 100ml): 45 kcal, 0.7g protein, 0.2g fat, 10g carbohydrates. Each bottle contains 1.5L. Price/L = 1€", 1.5, 25, "orange_juice", "pcs", false, null, true, 0.2, 1.5-0.2*1.5))
                dao.insert(Product(null, "Vodka", "Drinks", "Absolut Vodka. Ingredients: Grain spirit, distilled water. Alcohol: 40%. Each bottle contains 700 ml. Price/L = 24.3€", 17.0, 10, "vodka", "pcs", false, null, true, 0.3, 17.0-3*17.0))
                dao.insert(Product(null, "Malibu", "Drinks", "Malibu Coconut Rum. Ingredients: Rum with natural coconut flavor.Alcohol: 21%. Each bottle contains 700ml. Price/L = 17.8€", 12.5, 15, "malibu", "pcs", false, null, false, 0.0, 12.5))
                //Cleaning
                dao.insert(Product(null, "Laundry Detergent", "Cleaning", "Ingredients: Anionic/non-ionic surfactants, enzymes, fragrance. Capacity: 20 washes", 9.7, 10, "laundry_detergent", "pcs", false, null, true, 0.3, 9.7-0.3*9.7))
                dao.insert(Product(null, "Dish Detergent", "Cleaning", "Ingredients: Surfactants, lemon flavor, fat enhancer. Capacity: 400ml = 80 plates.", 4.7, 20, "dish_detergent", "pcs", false, null, false, 0.0, 4.7))
                dao.insert(Product(null, "Bleach", "Cleaning", "Bleach for cleaning every surface. Ingredients: Sodium hypochlorite 5%. Properties: Antiseptic, cleanser, disinfectant.", 6.8, 25, "bleach", "pcs", true, "1+1", false, 0.0, 6.8))
                dao.insert(Product(null , "Sponge", "Cleaning", "Material: Sponge with green wire. Use: Daily cleaning of surfaces and dishes. Two sponges are included.", 1.2, 30, "sponge", "pcs", true, "1+1", false, 0.0, 1.2))
                dao.insert(Product(null, "Gloves", "Cleaning", "Powder-free latex protective gloves. Use: Disposable, suitable for cleaning/cooking.", 3.5, 10, "gloves", "pcs", false, null, false, 0.0, 3.5))
                //Accessories
                dao.insert(Product(null, "AEK Scarf", "Accessories", "AEK supporter scarf", 10.0, 25, "aek", "pcs", false, null, false, 0.0, 10.0))
                dao.insert(Product(null, "PAOK Mug", "Accessories", "PAOK supporter mug", 4.5, 30, "paok", "pcs", false, null, false, 0.0, 4.5))
            }
        }
    }
}
