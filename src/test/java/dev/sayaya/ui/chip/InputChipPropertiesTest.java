package dev.sayaya.ui.chip;

import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class InputChipPropertiesTest {
    public static void test() {
        printSectionHeader("5. Input Chip 속성 (Input Chip Properties)");
        printDescription("Input Chip 전용 속성들입니다.");
        printSeparator();

        var inputSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(inputSection);

        inputSection.appendChild(h(3).text("Input Chip Properties").element());

        // Avatar
        var avatarExample = addExampleCode(inputSection,
            "📘 Avatar",
            "아바타 스타일을 적용합니다.",
            """
            var chip = chips()
                .input()
                .label("User")
                .icon("person")
                .avatar()
                .element();
            """);
        var avatarChip = chips()
                .input()
                .label("User")
                .icon("person")
                .avatar()
                .element();
        avatarExample.addInteractiveDemo(avatarChip, false);
        assertTrue("input 칩 avatar: true",
                avatarChip.avatar);

        // Selected
        var selectedExample = addExampleCode(inputSection,
            "📘 Selected",
            "Chip을 선택된 상태로 설정합니다.",
            """
            var chip = chips()
                .input()
                .label("Selected")
                .select()
                .element();
            """);
        var selectedChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Selected")
                .select()
                .element();
        selectedExample.addInteractiveDemo(selectedChip, false);
        assertTrue("input 칩 selected: true",
                selectedChip.selected);

        // Remove Only
        var removeOnlyExample = addExampleCode(inputSection,
            "📘 Remove Only",
            "제거 버튼만 표시하고 선택은 비활성화합니다.",
            """
            var chip = chips()
                .input()
                .label("Remove Only")
                .removeOnly()
                .element();
            """);
        var removeOnlyChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Remove Only")
                .removeOnly()
                .element();
        removeOnlyExample.addInteractiveDemo(removeOnlyChip, false);
        assertTrue("input 칩 removeOnly: true",
                removeOnlyChip.removeOnly);

        // Href
        var linkExample = addExampleCode(inputSection,
            "📘 Link (Href)",
            "Chip을 링크로 만듭니다.",
            """
            var chip = chips()
                .input()
                .label("Link")
                .href("https://example.com")
                .element();
            """);
        var linkChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Link")
                .href("https://example.com")
                .element();
        linkExample.addInteractiveDemo(linkChip, false);
        assertEquals("input 칩 href: https://example.com",
                "https://example.com", linkChip.href);

        // Target
        var targetExample = addExampleCode(inputSection,
            "📘 Target",
            "링크를 새 탭에서 열도록 설정합니다.",
            """
            var chip = chips()
                .input()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
            """);
        var targetChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
        targetExample.addInteractiveDemo(targetChip, false);
        assertEquals("input 칩 target: _blank",
                "_blank", targetChip.target);

        // Aria Label Remove
        var ariaRemoveExample = addExampleCode(inputSection,
            "📘 Aria Label Remove",
            "제거 버튼의 접근성 레이블을 설정합니다.",
            """
            var chip = chips()
                .input()
                .label("Remove")
                .ariaLabelRemove("Remove this chip")
                .element();
            """);
        var ariaRemoveChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Remove")
                .ariaLabelRemove("Remove this chip")
                .element();
        ariaRemoveExample.addInteractiveDemo(ariaRemoveChip, false);
        assertEquals("input 칩 ariaLabelRemove: Remove this chip",
                "Remove this chip", ariaRemoveChip.ariaLabelRemove);
    }
}
