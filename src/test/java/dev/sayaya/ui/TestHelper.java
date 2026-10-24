package dev.sayaya.ui;

import elemental2.dom.CSSStyleDeclaration;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class TestHelper {
    /** 계산된 스타일(window.getComputedStyle) */
    public static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    /** 의사 요소(::before 등)의 계산된 스타일 */
    public static CSSStyleDeclaration cs(Element e, String pseudo) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e, pseudo));
    }
    /**
     * 외부 URL 이미지. 페이지 load 이벤트가 끝난 뒤에 src 를 넣는다.
     * 테스트 러너는 load 와 NETWORKIDLE 을 기다리므로, 자동화 브라우저(navigator.webdriver)에서는 src 를 넣지 않는다(data-src 만 남김).
     * 실제 브라우저(GitHub Pages 데모)에서는 load 뒤에 외부 이미지를 받는다.
     */
    public static elemental2.dom.HTMLImageElement deferredImg(String url) {
        elemental2.dom.HTMLImageElement img = img().attr("alt", "").attr("draggable", "false").attr("data-src", url).element();
        // 자동화 브라우저(Playwright: navigator.webdriver=true)에서는 외부 이미지를 받지 않는다 — NETWORKIDLE 대기가 외부 서비스 속도에 묶이지 않게.
        if (Js.isTruthy(Js.asPropertyMap(DomGlobal.navigator).get("webdriver"))) return img;
        Runnable set = () -> img.src = url;
        if ("complete".equals(DomGlobal.document.readyState)) DomGlobal.setTimeout(t -> set.run(), 0);
        else DomGlobal.window.addEventListener("load", e -> set.run());
        return img;
    }
    /** ms 뒤에 실행 */
    public static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            log(message + " - PASS");
            return;
        }
        if (expected != null && expected.equals(actual)) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertEquals(String message, int expected, int actual) {
        if (expected == actual) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertTrue(String message, boolean condition) {
        if (condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    public static void assertFalse(String message, boolean condition) {
        if (!condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    public static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Object is null");
        }
    }

    public static void log(String message) {
        console.log(message);
    }

    public static void printSectionHeader(String header) {
        console.log(header);
    }

    public static void printDescription(String description) {
        console.log(description);
    }

    public static void printSeparator() {
        console.log("-".repeat(60) + "\n");
    }

    /**
     * 자동화 브라우저(Playwright 등, navigator.webdriver === true)에서 실행 중인지.
     * 사람이 페이지를 볼 때는 열기·클릭 같은 상호작용을 자동으로 재생하지 않고, 데모 버튼으로만 반응하게 하는 데 쓴다.
     */
    public static boolean isAutomated() {
        return Boolean.TRUE.equals(jsinterop.base.Js.asPropertyMap(elemental2.dom.DomGlobal.navigator).get("webdriver"));
    }
    /** 기존 테스트와 같은 테두리 섹션(제목 포함)을 만들어 body 에 붙인다. */
    public static HTMLElement section(String title) {
        var s = div().style("margin", "20px").style("padding", "20px").style("border", "1px solid #ddd").style("border-radius", "8px").element();
        body().add(s);
        s.appendChild(h(3).text(title).element());
        return s;
    }
    /** 예제 코드 + 실제 데모를 함께 보여 준다. */
    public static void demo(HTMLElement container, String title, String description, String code, HTMLElement element) {
        addExampleCode(container, title, description, code).addInteractiveDemo(element, false);
    }
    public static ExampleSection addExampleCode(HTMLElement container, String title, String description, String code) {
        var exampleDiv = div()
                .style("margin-top", "20px")
                .style("padding", "15px")
                .style("background", "#f8f9fa")
                .style("border-left", "4px solid #007bff")
                .style("border-radius", "4px")
                .element();

        var titleElement = div()
                .style("font-weight", "bold")
                .style("color", "#007bff")
                .style("margin-bottom", "8px")
                .text(title)
                .element();
        exampleDiv.appendChild(titleElement);

        if (description != null && !description.isEmpty()) {
            var descElement = div()
                    .style("color", "#666")
                    .style("margin-bottom", "10px")
                    .style("font-size", "14px")
                    .text(description)
                    .element();
            exampleDiv.appendChild(descElement);
        }

        var codeElement = pre()
                .add(code().text(code))
                .style("background", "#fff")
                .style("padding", "12px")
                .style("border-radius", "4px")
                .style("border", "1px solid #ddd")
                .style("overflow-x", "auto")
                .style("font-size", "13px")
                .style("margin", "0")
                .element();
        exampleDiv.appendChild(codeElement);

        container.appendChild(exampleDiv);

        return new ExampleSection(exampleDiv);
    }

    public record ExampleSection(HTMLElement container) {
        public HTMLElement addInteractiveDemo(HTMLElement element) {
            return addInteractiveDemo(element, true);
        }

        public HTMLElement addInteractiveDemo(HTMLElement element, boolean showStateInfo) {
            var demoCard = div()
                    .style("margin-top", "12px")
                    .style("padding", "15px")
                    .style("background", "#fff")
                    .style("border", "1px solid #dee2e6")
                    .style("border-radius", "4px")
                    .element();

            var demoLabel = div()
                    .style("font-size", "11px")
                    .style("color", "#6c757d")
                    .style("margin-bottom", "10px")
                    .style("font-weight", "500")
                    .text("▶ Interactive Demo")
                    .element();
            demoCard.appendChild(demoLabel);

            var elementWrapper = div()
                    .style("display", "flex")
                    .style("align-items", "center")
                    .style("gap", "12px")
                    .style("margin-bottom", showStateInfo ? "10px" : "0")
                    .element();
            elementWrapper.appendChild(element);
            demoCard.appendChild(elementWrapper);

            HTMLElement stateInfo = null;
            if (showStateInfo) {
                stateInfo = div()
                        .style("padding", "8px 10px")
                        .style("background", "#f8f9fa")
                        .style("border-left", "3px solid #007bff")
                        .style("border-radius", "2px")
                        .style("font-size", "12px")
                        .style("font-family", "monospace")
                        .style("color", "#495057")
                        .element();
                demoCard.appendChild(stateInfo);
            }

            container.appendChild(demoCard);

            return stateInfo;
        }
    }
}
