package dev.sayaya.ui.dialog;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.DialogElementBuilder.dialog;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class DialogEventsTest {
    public static void test() {
        printSectionHeader("3. Dialog 이벤트 (Events)");
        printDescription("Dialog 이벤트를 처리하는 방법:");
        printDescription("- onOpen(): 열리기 시작 이벤트");
        printDescription("- onOpened(): 열림 완료 이벤트");
        printDescription("- onClose(): 닫히기 시작 이벤트");
        printDescription("- onClosed(): 닫힘 완료 이벤트");
        printDescription("- onCancel(): 취소 이벤트 (ESC키, 배경 클릭)");
        printSeparator();

        var eventSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(eventSection);

        eventSection.appendChild(h(3).text("Dialog Events").element());

        // Open Event
        var openExample = addExampleCode(eventSection,
            "📘 Open Event (열림 이벤트)",
            "다이얼로그가 열리기 시작할 때 발생합니다.",
            """
            var openCount = new AtomicInteger(0);
            var openDialog = dialog()
                .headline("Open 이벤트")
                .content("다이얼로그 내용")
                .actions(div().add(button().text("닫기")))
                .onOpen(evt -> {
                    openCount.incrementAndGet();
                    console.log("open 이벤트 발생");
                })
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> openDialog.show());
            """);
        var openCount = new AtomicInteger(0);
        var openDialog = dialog()
                .headline("Open 이벤트")
                .content("다이얼로그 내용")
                .actions(div().add(button().text().text("닫기")))
                .onOpen(evt -> {
                    openCount.incrementAndGet();
                    console.log("open 이벤트 발생");
                })
                .element();
        var openButton = button().text().text("열기").element();
        var openDemoContainer = div()
                .add(openButton)
                .add(openDialog)
                .element();
        var openState = openExample.addInteractiveDemo(openDemoContainer);
        openState.textContent = "open 이벤트 횟수: " + openCount.get();
        openButton.addEventListener("click", evt -> openDialog.show());
        openDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> openDialog.close());
        openDialog.addEventListener("open", evt -> {
            openState.textContent = "open 이벤트 횟수: " + openCount.get();
        });

        // Opened Event
        var openedExample = addExampleCode(eventSection,
            "📘 Opened Event (열림 완료 이벤트)",
            "다이얼로그가 완전히 열렸을 때 발생합니다 (애니메이션 완료 후).",
            """
            var openedCount = new AtomicInteger(0);
            var openedDialog = dialog()
                .headline("Opened 이벤트")
                .content("애니메이션 완료 감지")
                .actions(div().add(button().text("닫기")))
                .onOpened(evt -> {
                    openedCount.incrementAndGet();
                    console.log("opened 이벤트 발생");
                })
                .element();

            var openedButton = button().text("열기").element();
            openedButton.addEventListener("click", evt -> openedDialog.show());
            """);
        var openedCount = new AtomicInteger(0);
        var openedDialog = dialog()
                .headline("Opened 이벤트")
                .content("애니메이션 완료 감지")
                .actions(div().add(button().text().text("닫기")))
                .onOpened(evt -> {
                    openedCount.incrementAndGet();
                    console.log("opened 이벤트 발생");
                })
                .element();
        var openedButton = button().text().text("열기").element();
        var openedDemoContainer = div()
                .add(openedButton)
                .add(openedDialog)
                .element();
        var openedState = openedExample.addInteractiveDemo(openedDemoContainer);
        openedState.textContent = "opened 이벤트 횟수: " + openedCount.get();
        openedButton.addEventListener("click", evt -> openedDialog.show());
        openedDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> openedDialog.close());
        openedDialog.addEventListener("opened", evt -> {
            openedState.textContent = "opened 이벤트 횟수: " + openedCount.get();
        });

        // Close Event
        var closeExample = addExampleCode(eventSection,
            "📘 Close Event (닫힘 이벤트)",
            "다이얼로그가 닫히기 시작할 때 발생합니다.",
            """
            var closeCount = new AtomicInteger(0);
            var closeDialog = dialog()
                .headline("Close 이벤트")
                .content("닫힘 감지")
                .actions(div().add(button().text("닫기")))
                .onClose(evt -> {
                    closeCount.incrementAndGet();
                    console.log("close 이벤트 발생");
                })
                .element();

            var closeButton = button().text("열기").element();
            closeButton.addEventListener("click", evt -> closeDialog.show());
            """);
        var closeCount = new AtomicInteger(0);
        var closeDialog = dialog()
                .headline("Close 이벤트")
                .content("닫힘 감지")
                .actions(div().add(button().text().text("닫기")))
                .onClose(evt -> {
                    closeCount.incrementAndGet();
                    console.log("close 이벤트 발생");
                })
                .element();
        var closeButton = button().text().text("열기").element();
        var closeDemoContainer = div()
                .add(closeButton)
                .add(closeDialog)
                .element();
        var closeState = closeExample.addInteractiveDemo(closeDemoContainer);
        closeState.textContent = "close 이벤트 횟수: " + closeCount.get();
        closeButton.addEventListener("click", evt -> closeDialog.show());
        closeDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> closeDialog.close());
        closeDialog.addEventListener("close", evt -> {
            closeState.textContent = "close 이벤트 횟수: " + closeCount.get();
        });

        // Closed Event
        var closedExample = addExampleCode(eventSection,
            "📘 Closed Event (닫힘 완료 이벤트)",
            "다이얼로그가 완전히 닫혔을 때 발생합니다 (애니메이션 완료 후).",
            """
            var closedCount = new AtomicInteger(0);
            var closedDialog = dialog()
                .headline("Closed 이벤트")
                .content("닫힘 완료 감지")
                .actions(div().add(button().text("닫기")))
                .onClosed(evt -> {
                    closedCount.incrementAndGet();
                    console.log("closed 이벤트 발생");
                })
                .element();

            var closedButton = button().text("열기").element();
            closedButton.addEventListener("click", evt -> closedDialog.show());
            """);
        var closedCount = new AtomicInteger(0);
        var closedDialog = dialog()
                .headline("Closed 이벤트")
                .content("닫힘 완료 감지")
                .actions(div().add(button().text().text("닫기")))
                .onClosed(evt -> {
                    closedCount.incrementAndGet();
                    console.log("closed 이벤트 발생");
                })
                .element();
        var closedButton = button().text().text("열기").element();
        var closedDemoContainer = div()
                .add(closedButton)
                .add(closedDialog)
                .element();
        var closedState = closedExample.addInteractiveDemo(closedDemoContainer);
        closedState.textContent = "closed 이벤트 횟수: " + closedCount.get();
        closedButton.addEventListener("click", evt -> closedDialog.show());
        closedDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> closedDialog.close());
        closedDialog.addEventListener("closed", evt -> {
            closedState.textContent = "closed 이벤트 횟수: " + closedCount.get();
        });

        // Cancel Event
        var cancelExample = addExampleCode(eventSection,
            "📘 Cancel Event (취소 이벤트)",
            "ESC 키를 누르거나 배경(scrim)을 클릭하면 발생합니다.",
            """
            var cancelCount = new AtomicInteger(0);
            var cancelDialog = dialog()
                .headline("Cancel 이벤트")
                .content("ESC 키를 누르거나 배경을 클릭해보세요")
                .actions(div().add(button().text("닫기")))
                .onCancel(evt -> {
                    cancelCount.incrementAndGet();
                    console.log("cancel 이벤트 발생");
                })
                .element();

            var cancelButton = button().text("열기").element();
            cancelButton.addEventListener("click", evt -> cancelDialog.show());
            """);
        var cancelCount = new AtomicInteger(0);
        var cancelDialog = dialog()
                .headline("Cancel 이벤트")
                .content("ESC 키를 누르거나 배경을 클릭해보세요")
                .actions(div().add(button().text().text("닫기")))
                .onCancel(evt -> {
                    cancelCount.incrementAndGet();
                    console.log("cancel 이벤트 발생");
                })
                .element();
        var cancelButton = button().text().text("열기").element();
        var cancelDemoContainer = div()
                .add(cancelButton)
                .add(cancelDialog)
                .element();
        var cancelState = cancelExample.addInteractiveDemo(cancelDemoContainer);
        cancelState.textContent = "cancel 이벤트 횟수: " + cancelCount.get();
        cancelButton.addEventListener("click", evt -> cancelDialog.show());
        cancelDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> cancelDialog.close());
        cancelDialog.addEventListener("cancel", evt -> {
            cancelState.textContent = "cancel 이벤트 횟수: " + cancelCount.get();
        });

        // All Events Combined
        var allEventsExample = addExampleCode(eventSection,
            "📘 All Events (모든 이벤트)",
            "모든 다이얼로그 이벤트를 추적합니다.",
            """
            var allOpen = new AtomicInteger(0);
            var allOpened = new AtomicInteger(0);
            var allClose = new AtomicInteger(0);
            var allClosed = new AtomicInteger(0);
            var allCancel = new AtomicInteger(0);

            var allEventsDialog = dialog()
                .headline("모든 이벤트")
                .content("다이얼로그를 열고 닫으면서 이벤트를 확인하세요")
                .actions(div().add(button().text("닫기")))
                .onOpen(evt -> allOpen.incrementAndGet())
                .onOpened(evt -> allOpened.incrementAndGet())
                .onClose(evt -> allClose.incrementAndGet())
                .onClosed(evt -> allClosed.incrementAndGet())
                .onCancel(evt -> allCancel.incrementAndGet())
                .element();

            var allEventsButton = button().text("열기").element();
            allEventsButton.addEventListener("click", evt -> allEventsDialog.show());
            """);
        var allOpen = new AtomicInteger(0);
        var allOpened = new AtomicInteger(0);
        var allClose = new AtomicInteger(0);
        var allClosed = new AtomicInteger(0);
        var allCancel = new AtomicInteger(0);

        var allEventsDialog = dialog()
                .headline("모든 이벤트")
                .content("다이얼로그를 열고 닫으면서 이벤트를 확인하세요")
                .actions(div().add(button().text().text("닫기")))
                .onOpen(evt -> allOpen.incrementAndGet())
                .onOpened(evt -> allOpened.incrementAndGet())
                .onClose(evt -> allClose.incrementAndGet())
                .onClosed(evt -> allClosed.incrementAndGet())
                .onCancel(evt -> allCancel.incrementAndGet())
                .element();
        var allEventsButton = button().text().text("열기").element();
        var allEventsDemoContainer = div()
                .add(allEventsButton)
                .add(allEventsDialog)
                .element();
        var allEventsState = allEventsExample.addInteractiveDemo(allEventsDemoContainer);

        var updateAllEventsState = new Runnable() {
            @Override
            public void run() {
                allEventsState.textContent = "open: " + allOpen.get() +
                    " | opened: " + allOpened.get() +
                    " | close: " + allClose.get() +
                    " | closed: " + allClosed.get() +
                    " | cancel: " + allCancel.get();
            }
        };
        updateAllEventsState.run();
        allEventsButton.addEventListener("click", evt -> allEventsDialog.show());
        allEventsDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> allEventsDialog.close());
        allEventsDialog.addEventListener("open", evt -> updateAllEventsState.run());
        allEventsDialog.addEventListener("opened", evt -> updateAllEventsState.run());
        allEventsDialog.addEventListener("close", evt -> updateAllEventsState.run());
        allEventsDialog.addEventListener("closed", evt -> updateAllEventsState.run());
        allEventsDialog.addEventListener("cancel", evt -> updateAllEventsState.run());

        assertEquals("초기 이벤트 횟수", 0, allOpen.get());
    }
}
