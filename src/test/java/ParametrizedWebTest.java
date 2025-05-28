import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
public class ParametrizedWebTest {

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1600x900";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
    }
    @ValueSource(strings = {
            "Enterprise", "Advanced Security"
    })
    @ParameterizedTest(name = "Должен убедиться что есть {0}")
    @Tag("BLOCK")
    void successfulFindEnterpriseTest(String value) {
        open("https://www.github.com");
        $$("ul li").findBy(text("Solutions")).hover();
        $$("ul a").findBy(text("Enterprises")).click();
        $$("div a").findBy(text(value)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Enterprises , Enterprise",
            "Nonprofits , By industry"
    })
    @ParameterizedTest(name = "Должен убедиться что в {0} есть {1}")
    void successfulOpenTwoPagesTest(String value, String expectedResult) {
        open("https://www.github.com");
        $$("ul li").findBy(text("Solutions")).hover();
        $$("ul a").findBy(text(value)).click();
        $$("div a").findBy(text(expectedResult)).shouldBe(visible);
    }
    static Stream<Arguments> selenideSiteShouldOpenCorrectPage () {
        return Stream.of(
                Arguments.of("Solutions", "Enterprises", "Enterprise"),
                Arguments.of("Product", "Actions", "Features")
        );

    }
    @MethodSource
    @ParameterizedTest(name = "Должен открыть {1} и убедиться что есть {2}")
    void selenideSiteShouldOpenCorrectPage(String headerMenu, String companySize, String expectedResult) {
        open("https://www.github.com");
        $$("ul li").findBy(text(headerMenu)).hover();
        $$("ul a").findBy(text(companySize)).click();
        $$("div a").findBy(text(expectedResult)).shouldBe(visible);
    }
}