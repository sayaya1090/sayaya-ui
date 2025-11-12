package dev.sayaya.ui.radio;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.RadioElementBuilder.radio;
import static org.jboss.elemento.Elements.*;

public class RadioGroupTest {
    public static void test() {
        printSectionHeader("3. 라디오 그룹 (Radio Group)");
        printDescription("같은 name을 가진 라디오 버튼들의 그룹 동작을 테스트합니다:");
        printDescription("- 같은 그룹 내에서는 하나만 선택 가능");
        printDescription("- 다른 그룹은 독립적으로 동작");
        printSeparator();

        var groupSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(groupSection);

        groupSection.appendChild(h(3).text("Radio Group").element());

        // Single Group - Exclusive Selection
        var singleGroupExample = addExampleCode(groupSection,
            "📘 Single Group (단일 그룹)",
            "같은 name을 가진 라디오 버튼들은 하나만 선택됩니다.",
            """
            var container = div().element();
            
            var radio1 = radio()
                .name("size")
                .value("small")
                .ariaLabel("Small")
                .element();
            
            var radio2 = radio()
                .name("size")
                .value("medium")
                .select(true)
                .ariaLabel("Medium")
                .element();
            
            var radio3 = radio()
                .name("size")
                .value("large")
                .ariaLabel("Large")
                .element();
            
            container.appendChild(label().add("Small: ").add(radio1).element());
            container.appendChild(label().add("Medium: ").add(radio2).element());
            container.appendChild(label().add("Large: ").add(radio3).element());
            """);
        
        var singleGroupContainer = div()
                .style("display", "flex")
                .style("flex-direction", "column")
                .style("gap", "10px")
                .element();
        
        var radio1 = radio()
                .name("size")
                .value("small")
                .ariaLabel("Small")
                .element();
        
        var radio2 = radio()
                .name("size")
                .value("medium")
                .select(true)
                .ariaLabel("Medium")
                .element();
        
        var radio3 = radio()
                .name("size")
                .value("large")
                .ariaLabel("Large")
                .element();
        
        singleGroupContainer.appendChild(label().add("Small: ").add(radio1).element());
        singleGroupContainer.appendChild(label().add("Medium: ").add(radio2).element());
        singleGroupContainer.appendChild(label().add("Large: ").add(radio3).element());
        
        var singleGroupState = singleGroupExample.addInteractiveDemo(singleGroupContainer);
        var updateSingleGroupState = new Runnable() {
            @Override
            public void run() {
                singleGroupState.textContent = "Small: " + radio1.checked + 
                    " | Medium: " + radio2.checked + 
                    " | Large: " + radio3.checked;
            }
        };
        updateSingleGroupState.run();
        
        radio1.addEventListener("change", evt -> updateSingleGroupState.run());
        radio2.addEventListener("change", evt -> updateSingleGroupState.run());
        radio3.addEventListener("change", evt -> updateSingleGroupState.run());
        
        assertTrue("초기 상태: Medium만 선택되어야 함", !radio1.checked && radio2.checked && !radio3.checked);

        // Multiple Groups - Independent Selection
        var multiGroupExample = addExampleCode(groupSection,
            "📘 Multiple Groups (다중 그룹)",
            "다른 name을 가진 그룹들은 독립적으로 동작합니다.",
            """
            var container = div().element();
            
            // Color group
            var colorRed = radio()
                .name("color")
                .value("red")
                .ariaLabel("Red")
                .element();
            
            var colorBlue = radio()
                .name("color")
                .value("blue")
                .select(true)
                .ariaLabel("Blue")
                .element();
            
            // Size group
            var sizeSmall = radio()
                .name("size2")
                .value("small")
                .select(true)
                .ariaLabel("Small")
                .element();
            
            var sizeLarge = radio()
                .name("size2")
                .value("large")
                .ariaLabel("Large")
                .element();
            """);
        
        var multiGroupContainer = div()
                .style("display", "flex")
                .style("flex-direction", "column")
                .style("gap", "15px")
                .element();
        
        var colorGroup = div()
                .style("border", "1px solid #e0e0e0")
                .style("padding", "10px")
                .style("border-radius", "4px")
                .element();
        colorGroup.appendChild(div().style("font-weight", "bold").style("margin-bottom", "5px").text("Color Group:").element());
        
        var colorRed = radio()
                .name("color")
                .value("red")
                .ariaLabel("Red")
                .element();
        
        var colorBlue = radio()
                .name("color")
                .value("blue")
                .select(true)
                .ariaLabel("Blue")
                .element();
        
        colorGroup.appendChild(label().add("Red: ").add(colorRed).element());
        colorGroup.appendChild(label().add("Blue: ").add(colorBlue).element());
        
        var sizeGroup = div()
                .style("border", "1px solid #e0e0e0")
                .style("padding", "10px")
                .style("border-radius", "4px")
                .element();
        sizeGroup.appendChild(div().style("font-weight", "bold").style("margin-bottom", "5px").text("Size Group:").element());
        
        var sizeSmall = radio()
                .name("size2")
                .value("small")
                .select(true)
                .ariaLabel("Small")
                .element();
        
        var sizeLarge = radio()
                .name("size2")
                .value("large")
                .ariaLabel("Large")
                .element();
        
        sizeGroup.appendChild(label().add("Small: ").add(sizeSmall).element());
        sizeGroup.appendChild(label().add("Large: ").add(sizeLarge).element());
        
        multiGroupContainer.appendChild(colorGroup);
        multiGroupContainer.appendChild(sizeGroup);
        
        var multiGroupState = multiGroupExample.addInteractiveDemo(multiGroupContainer);
        var updateMultiGroupState = new Runnable() {
            @Override
            public void run() {
                multiGroupState.textContent = "Color: Red=" + colorRed.checked + ", Blue=" + colorBlue.checked + 
                    " | Size: Small=" + sizeSmall.checked + ", Large=" + sizeLarge.checked;
            }
        };
        updateMultiGroupState.run();
        
        colorRed.addEventListener("change", evt -> updateMultiGroupState.run());
        colorBlue.addEventListener("change", evt -> updateMultiGroupState.run());
        sizeSmall.addEventListener("change", evt -> updateMultiGroupState.run());
        sizeLarge.addEventListener("change", evt -> updateMultiGroupState.run());
        
        assertTrue("초기 상태: Blue와 Small이 선택되어야 함", 
            !colorRed.checked && colorBlue.checked && sizeSmall.checked && !sizeLarge.checked);
    }
}
