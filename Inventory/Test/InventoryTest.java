import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Group of test methods to test the Inventory class.
 * @author Steven Barton
 */
public class InventoryTest {

    @Test
    public void addPartTest() {
        Inventory inv = new Inventory();

        // Checking inventory constructor function
        Assert.assertNotNull(inv);

        Part part0 = new InHouse(1, "thing1", 42.00, 4, 0,7, 4556);
        Part part1 = new Outsourced(2, "thing2", 57.00, 6, 0, 20, "partCorp");

        // Checking valid add
        Assert.assertTrue(inv.addPart(part0));
        // Checking values
        Assert.assertEquals(1, inv.getAllParts().size());
        // Checking valid add
        Assert.assertTrue(inv.addPart(part1));
        // Checking values
        Assert.assertEquals(2, inv.getAllParts().size());
        // Checking values
        Assert.assertEquals(1, inv.getAllParts().get(0).getId());
        // Checking values
        Assert.assertEquals(2, inv.getAllParts().get(1).getId());
        // Checking values
        Assert.assertEquals("thing1", inv.getAllParts().get(0).getName());
        // Checking values
        Assert.assertEquals("thing2", inv.getAllParts().get(1).getName());
        // Checking duplicate add
        Assert.assertFalse(inv.addPart(part0));
    }

    @Test
    public void addProductTest() {
        Inventory inv = new Inventory();

        Product prod1 = new Product(1, "testProd", 456.79, 5, 1, 50);
        Product prod2 = new Product(2, "tryMe", 299.99, 67, 1, 79);

        // Checking valid add
        Assert.assertTrue(inv.addProduct(prod1));
        // Checking values
        Assert.assertEquals(1, inv.getAllProducts().size());
        // Checking valid add
        Assert.assertTrue(inv.addProduct(prod2));
        // Checking values
        Assert.assertEquals(2, inv.getAllProducts().size());
        // Checking values
        Assert.assertEquals("testProd", inv.getAllProducts().get(0).getName());
        // Checking values
        Assert.assertEquals("tryMe", inv.getAllProducts().get(1).getName());
        // Checking duplicate add
        Assert.assertFalse(inv.addProduct(prod1));
    }

    @Test
    public void lookupPartIdTest() throws IOException {
        Inventory inv = new Inventory();

        // How should this method handle a call to an empty list?  Return Null or throw exception with a try/catch?
        try {
            Assert.assertNotNull(inv.lookupPart(1));
        }
        catch(IOException e) {
            //e.printStackTrace();
        }

        Part part0 = new InHouse(1, "thing1", 42.00, 4, 0,7, 4556);
        Part part1 = new Outsourced(2, "thing2", 57.00, 6, 0, 20, "partCorp");

        inv.addPart(part0);
        inv.addPart(part1);

        // Standard partId lookup
        Assert.assertEquals("thing1", inv.lookupPart(1).getName());
        Assert.assertEquals("thing2", inv.lookupPart(2).getName());

        // How should this handle looking up an ID that does not exist? (Related to empty list, same result?)
        try {
            Assert.assertNotNull(inv.lookupPart(3).getName());
        }
        catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void lookupProductIdTest() throws IOException {
        Inventory inv = new Inventory();

        // How should this method handle a lookup on an empty list? IOException should be thrown.
        try {
            Assert.assertNotNull(inv.lookupProduct(6));
        }
        catch (IOException e) {
            // Code here as needed, set to ignored instead of catch?
        }

        Product prod1 = new Product(1, "testProd", 456.79, 5, 1, 50);
        Product prod2 = new Product(2, "tryMe", 299.99, 67, 1, 79);

        inv.addProduct(prod1);
        inv.addProduct(prod2);

        Assert.assertEquals("testProd", inv.lookupProduct(1).getName());
        Assert.assertEquals("tryMe", inv.lookupProduct(2).getName());

        try {
            Assert.assertNotNull(inv.lookupProduct(8));
        }
        catch (IOException e) {
            // handling code here if needed
        }
    }

    @Test
    public void lookupPartStringTest() {
        Inventory inv = new Inventory();

        // How should this method handle a lookup on an empty list?  Return an empty list.
        Assert.assertEquals(0, inv.lookupPart("thing").size());

        Part part0 = new InHouse(1, "thing1", 42.00, 4, 0,7, 4556);
        Part part1 = new Outsourced(2, "thing2", 57.00, 6, 0, 20, "partCorp");

        inv.addPart(part0);
        inv.addPart(part1);

        Assert.assertEquals(1, inv.lookupPart("thing1").get(0).getId());
        Assert.assertNotEquals(2, inv.lookupPart("thing1").get(0).getId());

        Assert.assertEquals(2, inv.lookupPart("thing2").get(0).getId());
        Assert.assertNotEquals(1, inv.lookupPart("thing2").get(0).getId());

        Assert.assertEquals(2, inv.lookupPart("hing").size());

        Assert.assertEquals(0, inv.lookupPart("alligator").size());

    }

    @Test
    public void lookupProductStringTest() {
        Inventory inv = new Inventory();

        // How should this method handle a lookup on an empty list? Again, return an empty list.
        Assert.assertEquals(0, inv.lookupProduct("t").size());

        Product prod1 = new Product(1, "testProd", 456.79, 5, 1, 50);
        Product prod2 = new Product(2, "tryMe", 299.99, 67, 1, 79);

        inv.addProduct(prod1);
        inv.addProduct(prod2);

        Assert.assertEquals(1, inv.lookupProduct("testProd").get(0).getId());
        Assert.assertEquals(1, inv.lookupProduct("test").get(0).getId());
        Assert.assertEquals(2, inv.lookupProduct("try").get(0).getId());
        Assert.assertEquals(2, inv.lookupProduct("tryMe").get(0).getId());
        Assert.assertEquals(2, inv.lookupProduct("t").size());

        Assert.assertEquals(0, inv.lookupProduct("itsnotatumor").size());
    }

    @Test
    public void updatePartTest() throws IOException {
        Inventory inv = new Inventory();

        Part part0 = new InHouse(1, "thing1", 42.00, 4, 0,7, 4556);
        Part part1 = new Outsourced(2, "thing2", 57.00, 6, 0, 20, "partCorp");

        inv.addPart(part0);
        inv.addPart(part1);

        Part part1Update = new InHouse(1, "thing1", 699.99, 3, 0, 7, 4556);

        inv.updatePart(0, part1Update);

        Assert.assertEquals(699.99, inv.lookupPart(1).getPrice(), 0.0);
        Assert.assertEquals(3, inv.lookupPart(1).getStock());

    }

    @Test
    public void updateProductTest() throws IOException {
        Inventory inv = new Inventory();

        Product prod1 = new Product(1, "testProd", 456.79, 5, 1, 50);
        Product prod2 = new Product(2, "tryMe", 299.99, 67, 1, 79);

        inv.addProduct(prod1);
        inv.addProduct(prod2);

        Product updateProd = new Product(1, "provenProd", 456.79, 5, 1, 200);

        inv.updateProduct(0, updateProd);

        Assert.assertEquals("provenProd", inv.lookupProduct(1).getName());
        Assert.assertEquals(200, inv.lookupProduct(1).getMax());

    }

    @Test
    public void deletePartTest() {
        Inventory inv = new Inventory();

        // How should this method handle a delete attempt for a non existent item/empty list? Return false.

        Part part0 = new InHouse(1, "thing1", 42.00, 4, 0,7, 4556);
        Part part1 = new Outsourced(2, "thing2", 57.00, 6, 0, 20, "partCorp");
        Part part2 = new Outsourced(3, "thing3", 456.99, 89, 0, 90,
                "DunderMifflin");

        // Checking delete on empty list
        Assert.assertFalse(inv.deletePart(part0));

        inv.addPart(part0);
        inv.addPart(part1);

        // Checking delete of non-existent
        Assert.assertFalse(inv.deletePart(part2));

        inv.addPart(part2);

        // Checking values
        Assert.assertEquals("thing1", inv.getAllParts().get(0).getName());

        // Checking valid delete
        Assert.assertTrue(inv.deletePart(part0));

        // Checking values
        Assert.assertEquals("thing2", inv.getAllParts().get(0).getName());

        // Checking valid delete
        Assert.assertTrue(inv.deletePart(part1));

        // Checking final values
        Assert.assertEquals(1, inv.getAllParts().size());

    }

    @Test
    public void deleteProductTest() {
        Inventory inv = new Inventory();

        // How should this method handle a delete attempt for a non existent item/empty list? Return false.

        Product prod1 = new Product(1, "testProd", 456.79, 5, 1, 50);
        Product prod2 = new Product(2, "tryMe", 299.99, 67, 1, 79);
        Product prod3 = new Product(3, "Extra", 456.99, 45, 1, 47);

        // Testind delete on empty list
        Assert.assertFalse(inv.deleteProduct(prod1));

        inv.addProduct(prod1);
        inv.addProduct(prod2);

        // Testing delete of nonexistent
        Assert.assertFalse(inv.deleteProduct(prod3));

        inv.addProduct(prod3);

        // Checking values
        Assert.assertEquals(1, inv.getAllProducts().get(0).getId());

        // Checking valid delete
        Assert.assertTrue(inv.deleteProduct(prod1));

        // Checking values
        Assert.assertEquals(2, inv.getAllProducts().get(0).getId());

        // Checking valid delete
        Assert.assertTrue(inv.deleteProduct(prod2));

        // Checking final values
        Assert.assertEquals(1, inv.getAllProducts().size());
    }

    @Test
    public void getAllPartsTest() {
        Inventory inv = new Inventory();

        // How should this method handle an attempt on an empty list?  Return an empty list.
        Assert.assertEquals(0, inv.getAllParts().size());

        Part part0 = new InHouse(1, "thing1", 42.00, 4, 0,7, 4556);
        Part part1 = new Outsourced(2, "thing2", 57.00, 6, 0, 20, "partCorp");

        inv.addPart(part0);
        inv.addPart(part1);

        Assert.assertEquals(2, inv.getAllParts().size());

    }

    @Test
    public void getAllProductsTest() {
        Inventory inv = new Inventory();

        // How should this method handle an attempt on an empty list? Return an empty list.
        Assert.assertEquals(0, inv.getAllProducts().size());

        Product prod1 = new Product(1, "testProd", 456.79, 5, 1, 50);
        Product prod2 = new Product(2, "tryMe", 299.99, 67, 1, 79);

        inv.addProduct(prod1);
        inv.addProduct(prod2);

        Assert.assertEquals(2, inv.getAllProducts().size());

    }

}
