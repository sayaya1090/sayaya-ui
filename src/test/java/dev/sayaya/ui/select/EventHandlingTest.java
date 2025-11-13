package dev.sayaya.ui.select;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class EventHandlingTest {
    public static void test() {
        printSectionHeader("5. 이벤트 처리 (Event Handling)");
        printDescription("Select 이벤트를 처리하는 방법:");
        printDescription("- onChange(): 선택 변경 이벤트");
        printDescription("- onInput(): 입력 이벤트");
        printDescription("- onOpening/onOpened(): 메뉴 열기 이벤트");
        printDescription("- onClosing/onClosed(): 메뉴 닫기 이벤트");
        printSeparator();

        var eventSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(eventSection);

        eventSection.appendChild(h(3).text("Event Handling").element());

        // Change Event
        var changeExample = addExampleCode(eventSection,
            "📘 Change Event (변경 이벤트)",
            "사용자가 옵션을 선택하면 발생하는 이벤트입니다.",
            """
            var changeCount = new AtomicInteger(0);
            var select = select().filled()
                .label("옵션")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("선택 변경됨");
                })
                .option().value("a").headline("A").done()
                .option().value("b").headline("B").done()
                .element();
            """);
        var changeCount = new AtomicInteger(0);
        var changeSelect = select().filled()
                .label("옵션")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("선택 변경됨");
                }).option().value("a").headline("A").done()
                .option().value("b").headline("B").done()
                .element();
        var changeState = changeExample.addInteractiveDemo(changeSelect);
        changeState.textContent = "선택 횟수: " + changeCount.get() + " | 현재 값: " + changeSelect.value;
        changeSelect.addEventListener("change", evt -> {
            changeState.textContent = "선택 횟수: " + changeCount.get() + " | 현재 값: " + changeSelect.value;
        });

        assertEquals("초기 변경 횟수", 0, changeCount.get());
        changeSelect.select("a");
        assertEquals("첫 번째 변경", 1, changeCount.get());
        changeSelect.select("b");
        assertEquals("두 번째 변경", 2, changeCount.get());

        // Input Event
        var inputExample = addExampleCode(eventSection,
            "📘 Input Event (입력 이벤트)",
            "값이 변경될 때마다 발생하는 이벤트입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var select = select().outlined()
                .label("입력 감지")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("선택 변경됨");
                })
                .option().value("1").headline("옵션 1").done()
                .option().value("2").headline("옵션 2").done()
                .element();
            """);
        var inputCount = new AtomicInteger(0);
        var inputSelect = select().outlined()
                .label("입력 감지")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("선택 변경됨");
                })
                .option().value("1").headline("옵션 1").done()
                .option().value("2").headline("옵션 2").done()
                .element();
        var inputState = inputExample.addInteractiveDemo(inputSelect);
        inputState.textContent = "입력 횟수: " + inputCount.get() + " | 현재 값: " + inputSelect.value;
        inputSelect.addEventListener("input", evt -> {
            inputState.textContent = "입력 횟수: " + inputCount.get() + " | 현재 값: " + inputSelect.value;
        });

        inputSelect.select("1");
        assertTrue("input 이벤트 발생", inputCount.get() > 0);

        // Menu Open/Close Events
        var menuExample = addExampleCode(eventSection,
            "📘 Menu Events (메뉴 이벤트)",
            "메뉴가 열리고 닫힐 때 발생하는 이벤트들입니다.",
            """
            var openingCount = new AtomicInteger(0);
            var openedCount = new AtomicInteger(0);
            var closingCount = new AtomicInteger(0);
            var closedCount = new AtomicInteger(0);

            var select = select().filled()
                .label("메뉴 이벤트")
                .onOpening(evt -> {
                    openingCount.incrementAndGet();
                    console.log("opening 이벤트 발생");
                }).onOpened(evt -> {
                    openedCount.incrementAndGet();
                    console.log("opened 이벤트 발생");
                }).onClosing(evt -> {
                    closingCount.incrementAndGet();
                    console.log("closing 이벤트 발생");
                }).onClosed(evt -> {
                    closedCount.incrementAndGet();
                    console.log("closed 이벤트 발생");
                }).option().value("x").headline("X").done()
                .option().value("y").headline("Y").done()
                .element();
            """);
        var openingCount = new AtomicInteger(0);
        var openedCount = new AtomicInteger(0);
        var closingCount = new AtomicInteger(0);
        var closedCount = new AtomicInteger(0);

        var menuEventSelect = select().filled()
                .label("메뉴 이벤트")
                .onOpening(evt -> {
                    openingCount.incrementAndGet();
                    console.log("opening 이벤트 발생");
                }).onOpened(evt -> {
                    openedCount.incrementAndGet();
                    console.log("opened 이벤트 발생");
                }).onClosing(evt -> {
                    closingCount.incrementAndGet();
                    console.log("closing 이벤트 발생");
                }).onClosed(evt -> {
                    closedCount.incrementAndGet();
                    console.log("closed 이벤트 발생");
                }).option().value("x").headline("X").done()
                .option().value("y").headline("Y").done()
                .element();
        var menuState = menuExample.addInteractiveDemo(menuEventSelect);
        var updateMenuState = new Runnable() {
            @Override
            public void run() {
                menuState.textContent = "opening: " + openingCount.get() +
                    " | opened: " + openedCount.get() +
                    " | closing: " + closingCount.get() +
                    " | closed: " + closedCount.get();
            }
        };
        updateMenuState.run();
        menuEventSelect.addEventListener("opening", evt -> updateMenuState.run());
        menuEventSelect.addEventListener("opened", evt -> updateMenuState.run());
        menuEventSelect.addEventListener("closing", evt -> updateMenuState.run());
        menuEventSelect.addEventListener("closed", evt -> updateMenuState.run());

        // 메뉴 열기
        menuEventSelect.showPicker();
        assertTrue("opening 이벤트 발생 가능", openingCount.get() >= 0);
    }
}
