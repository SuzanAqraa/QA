package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeBookTest {

    RecipeBook book;
    Recipe recipe;

    @BeforeEach
    void setup() {
        book = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Green Tea");
    }

    @Test
    @DisplayName("Add a fresh recipe")
    @Order(1)
    void shouldAddNewRecipe() {
        assertTrue(book.addRecipe(recipe));
    }

    @Test
    @DisplayName("Avoid adding a duplicate recipe")
    @Order(2)
    void shouldNotAddDuplicate() {
        book.addRecipe(recipe);
        assertFalse(book.addRecipe(recipe));
    }

    @Test
    @DisplayName("Delete a recipe that exists")
    @Order(3)
    void shouldDeleteRecipeSuccessfully() {
        book.addRecipe(recipe);
        assertEquals("Green Tea", book.deleteRecipe(0));
    }

    @Test
    @DisplayName("Edit a recipe that exists")
    @Order(4)
    void shouldEditRecipeName() {
        book.addRecipe(recipe);
        Recipe updated = new Recipe();
        updated.setName("Black Coffee");
        assertEquals("Green Tea", book.editRecipe(0, updated));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mocha", "Herbal Tea", "Americano"})
    @DisplayName("Add various recipes using ValueSource")
    @Order(5)
    void addMultipleRecipes(String recipeName) {
        Recipe r = new Recipe();
        r.setName(recipeName);
        assertTrue(book.addRecipe(r));
    }

    @Test
    @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Recipe addition completes quickly")
    @Order(6)
    void shouldAddRecipeFast() {
        Recipe r = new Recipe();
        r.setName("Speedy Soup");
        assertTrue(book.addRecipe(r));
    }

    @Test
    @DisplayName("Should return null when deleting from empty slot")
    @Order(7)
    void shouldReturnNullOnDeleteFromEmpty() {
        assertNull(book.deleteRecipe(0));
    }

    @Test
    @DisplayName("Should return null when editing a non-existing recipe")
    @Order(8)
    void shouldReturnNullOnEditFromEmpty() {
        Recipe newR = new Recipe();
        newR.setName("Choco");
        assertNull(book.editRecipe(0, newR));
    }

    @Test
    @DisplayName("Should not add more than 4 recipes")
    @Order(9)
    void shouldNotExceedMaxRecipes() {
        for (int i = 0; i < 4; i++) {
            Recipe r = new Recipe();
            r.setName("R" + i);
            assertTrue(book.addRecipe(r));
        }
        Recipe extra = new Recipe();
        extra.setName("Extra");
        assertFalse(book.addRecipe(extra));
    }
    @Test
    @DisplayName("Should detect existing recipe and not add")
    @Order(10)
    void shouldNotAddExistingRecipe() {
        book.addRecipe(recipe); // add once
        assertFalse(book.addRecipe(recipe)); // try to add again
    }
    @Test
    @DisplayName("Should add recipe into first available slot")
    @Order(11)
    void shouldAddRecipeToFirstEmptySlot() {
        Recipe r = new Recipe();
        r.setName("Chamomile");
        assertTrue(book.addRecipe(r)); // triggers line 42
    }
    @Test
    @DisplayName("Should delete recipe when present")
    @Order(12)
    void shouldDeleteWhenRecipeExists() {
        book.addRecipe(recipe);
        assertEquals("Green Tea", book.deleteRecipe(0)); // triggers line 58
    }


}
