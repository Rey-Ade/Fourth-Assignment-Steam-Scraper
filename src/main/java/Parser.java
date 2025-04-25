import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Collections;

public class Parser {
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName(){
        List<Game> sortedByName = new ArrayList<>(games);
        // Sort games alphabetically (least)
        Collections.sort(sortedByName, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return g1.getName().compareToIgnoreCase(g2.getName());
            }
        });
        return sortedByName;
    }

    public List<Game> sortByRating(){
        List<Game> sortedByRating = new ArrayList<>(games);
        // Sort games by rating (most)
        Collections.sort(sortedByRating, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return Double.compare(g2.getRating(), g1.getRating());
            }
        });
        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);
        // Sort games by price (most)
        Collections.sort(sortedByPrice, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return Integer.compare(g2.getPrice(), g1.getPrice());
            }
        });
        return sortedByPrice;
    }

    public void setUp() throws IOException {
        //Parse the HTML file using Jsoup
        File html = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(html, "UTF-8");

        // Extract data from the HTML
        Elements games = doc.getElementsByClass("col-md-4 game");

        // Iterate through each Game div to extract Game data
        for (Element game : games) {
            // get game-name
            Elements name = game.getElementsByClass("game-name");
            String gameName = name.text();
            // get game-rating
            Elements rating = game.getElementsByClass("game-rating");
            String ratingText = rating.text();
            // get game-price
            Elements price = game.getElementsByClass("game-price");
            String priceText = price.text();
            // extract rating from gameRating string
            String gameRating = "";
            int i = 0;
            Character ch = ratingText.charAt(i);
            while(ch != '/') {
                gameRating += ch;
                i++;
                ch = ratingText.charAt(i);
            }
            // extract price from gamePrice string
            String gamePrice = "";
            i = 0;
            ch = priceText.charAt(i);
            while(ch != ' ') {
                gamePrice += ch;
                i++;
                ch = priceText.charAt(i);
            }

            // create a Game instance
            Game videoGame = new Game(gameName, Double.parseDouble(gameRating), Integer.parseInt(gamePrice));
            Parser.games.add(videoGame);
        }
    }

    public static void main(String[] args) {
        //you can test your code here before you run the unit tests
    }
}
