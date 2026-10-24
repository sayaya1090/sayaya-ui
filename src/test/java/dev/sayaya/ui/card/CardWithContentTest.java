package dev.sayaya.ui.card;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.CardElementBuilder.card;
import static org.jboss.elemento.Elements.*;

public class CardWithContentTest {
    public static void test() {
        printSectionHeader("3. 콘텐츠가 있는 Card (Card with Content)");
        printDescription("다양한 콘텐츠를 포함하는 카드를 생성합니다.");
        printSeparator();

        var contentSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(contentSection);

        contentSection.appendChild(h(3).text("Card with Rich Content").element());

        // Card with structured content
        var structuredExample = addExampleCode(contentSection,
            "📘 Card with Structured Content (구조화된 콘텐츠 카드)",
            "제목, 본문, 액션 버튼이 있는 구조화된 카드입니다.",
            """
            var card = card().elevated()
                .style("width", "192px")
                .add(div().style("padding", "16px")
                    .add(h(4).text("Card Title").style("margin", "0 0 8px 0"))
                    .add(p().text("카드 본문 내용입니다.").style("margin", "0 0 16px 0"))
                    .add(div().style("display", "flex").style("gap", "8px")
                        .add(button().filled().text("확인"))
                        .add(button().outlined().text("취소"))
                    )
                )
                .element();
            """);

        var structuredCard = card().elevated()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .add(h(4)
                        .text("Card Title")
                        .style("margin", "0 0 8px 0")
                    )
                    .add(p()
                        .text("카드 본문 내용입니다. Card는 관련된 정보를 그룹화하여 표시하는 컨테이너입니다.")
                        .style("margin", "0 0 16px 0")
                        .style("color", "#666")
                    )
                    .add(div()
                        .style("display", "flex")
                        .style("gap", "8px")
                        .add(button().filled().text("확인"))
                        .add(button().outlined().text("취소"))
                    )
                )
                .element();

        structuredExample.addInteractiveDemo(structuredCard, false);
        assertNotNull("card: 구조화된 카드 생성됨", structuredCard);

        // Card with media (외부 이미지 URL)
        var mediaExample = addExampleCode(contentSection,
            "📘 Card with Media (이미지가 있는 카드)",
            "카드 위쪽에 외부 이미지 URL 을 넣은 예시입니다.",
            """
            card().elevated().style("width", "280px").style("overflow", "hidden")
                .add(img("https://picsum.photos/seed/sayaya-card/560/320").attr("alt", "")
                    .style("width", "100%").style("height", "160px").style("object-fit", "cover").style("display", "block"))
                .add(div().style("padding", "16px")
                    .add(h(4).text("Olle trail"))
                    .add(p().text("..."))
                    .add(button().filled().text("예약")))
                .element();
            """);
        var mediaCard = card().elevated().style("width", "280px").style("overflow", "hidden")
                .add(img("https://picsum.photos/seed/sayaya-card/560/320").attr("alt", "")
                        .style("width", "100%").style("height", "160px").style("object-fit", "cover").style("display", "block"))
                .add(div().style("padding", "16px")
                        .add(h(4).text("Olle trail").style("margin", "0 0 8px 0"))
                        .add(p().text("바다를 따라 걷는 제주 올레길 코스를 소개합니다.").style("margin", "0 0 16px 0").style("color", "#666"))
                        .add(div().style("display", "flex").style("gap", "8px")
                                .add(button().filled().text("예약"))
                                .add(button().text().text("공유"))))
                .element();
        mediaExample.addInteractiveDemo(mediaCard, false);
        assertNotNull("card: 이미지 카드 생성됨", mediaCard);

        // Clickable Card
        var clickableExample = addExampleCode(contentSection,
            "📘 Clickable Card (클릭 가능한 카드)",
            "클릭 이벤트를 가진 카드입니다.",
            """
            var card = card().outlined()
                .style("width", "192px")
                .style("cursor", "pointer")
                .onClick(evt -> {
                    console.log("Card clicked!");
                })
                .add(div().style("padding", "16px")
                    .add(h(4).text("Clickable Card").style("margin", "0 0 8px 0"))
                    .add(p().text("이 카드를 클릭해보세요.").style("margin", "0"))
                )
                .element();
            """);

        var clickableCard = card().outlined()
                .style("width", "192px")
                .style("cursor", "pointer")
                .onClick(evt -> {
                    log("card: Clickable Card가 클릭됨");
                })
                .add(div()
                    .style("padding", "16px")
                    .add(h(4)
                        .text("Clickable Card")
                        .style("margin", "0 0 8px 0")
                    )
                    .add(p()
                        .text("이 카드를 클릭해보세요.")
                        .style("margin", "0")
                        .style("color", "#666")
                    )
                )
                .element();

        clickableExample.addInteractiveDemo(clickableCard, false);
        assertNotNull("card: 클릭 가능한 카드 생성됨", clickableCard);

        // Card with aria-label
        var accessibleExample = addExampleCode(contentSection,
            "📘 Accessible Card (접근 가능한 카드)",
            "aria-label이 설정된 접근성 높은 카드입니다.",
            """
            var card = card().filled()
                .style("width", "192px")
                .ariaLabel("Product information card")
                .add(div().style("padding", "16px")
                    .add(h(4).text("Product").style("margin", "0 0 8px 0"))
                    .add(p().text("제품 정보").style("margin", "0"))
                )
                .element();
            """);

        var accessibleCard = card().filled()
                .style("width", "192px")
                .ariaLabel("Product information card")
                .add(div()
                    .style("padding", "16px")
                    .add(h(4)
                        .text("Product")
                        .style("margin", "0 0 8px 0")
                    )
                    .add(p()
                        .text("제품 정보")
                        .style("margin", "0")
                        .style("color", "#666")
                    )
                )
                .element();

        accessibleExample.addInteractiveDemo(accessibleCard, false);
        assertEquals("card: aria-label 설정됨", "Product information card", accessibleCard.getAttribute("aria-label"));
    }
}
