package dev.sayaya.ui.button;

import dev.sayaya.ui.elements.FabElementBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class FabTest {
    public static void test() {
        printSectionHeader("9. 플로팅 액션 버튼 (FAB)");
        printDescription("화면 위에 떠있는 원형 액션 버튼:");
        printDescription("- Plain FAB: 표준 FAB (3가지 크기 지원)");
        printDescription("- Branded FAB: 브랜드 로고가 있는 FAB (2가지 크기 지원)");
        printDescription("- Extended FAB: 텍스트 레이블이 있는 확장형 FAB");
        printDescription("- Variants: Surface, Primary, Secondary, Tertiary");
        printSeparator();

        testFabSizes();
        testFabVariants();
        testExtendedFab();
        testBrandedFab();
        testFabProperties();
    }

    private static void testFabSizes() {
        var sizesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(sizesSection);

        sizesSection.appendChild(h(3).text("FAB Sizes").element());

        // Small FAB
        var smallExample = addExampleCode(sizesSection,
            "📘 Small FAB (작은 FAB)",
            "가장 작은 크기의 FAB입니다. 제한된 공간에서 사용합니다.",
            """
            var fab = button().fab()
                .size(PlainFabElementBuilder.Size.Small)
                .icon("add")
                .ariaLabel("추가")
                .element();
            """);
        var smallFab = button().fab()
                .size(FabElementBuilder.PlainFabElementBuilder.Size.Small)
                .icon("add")
                .ariaLabel("추가")
                .element();
        smallExample.addInteractiveDemo(smallFab, false);
        assertEquals("small FAB: size small", "small", smallFab.size);

        // Medium FAB (default)
        var mediumExample = addExampleCode(sizesSection,
            "📘 Medium FAB (중간 FAB - 기본)",
            "기본 크기의 FAB입니다.",
            """
            var fab = button().fab()
                .size(PlainFabElementBuilder.Size.Medium)
                .icon("edit")
                .ariaLabel("편집")
                .element();
            """);
        var mediumFab = button().fab()
                .size(FabElementBuilder.PlainFabElementBuilder.Size.Medium)
                .icon("edit")
                .ariaLabel("편집")
                .element();
        mediumExample.addInteractiveDemo(mediumFab, false);
        assertEquals("medium FAB: size medium", "medium", mediumFab.size);

        // Large FAB
        var largeExample = addExampleCode(sizesSection,
            "📘 Large FAB (큰 FAB)",
            "가장 큰 크기의 FAB입니다. 가장 중요한 액션에 사용합니다.",
            """
            var fab = button().fab()
                .size(PlainFabElementBuilder.Size.Large)
                .icon("favorite")
                .ariaLabel("즐겨찾기")
                .element();
            """);
        var largeFab = button().fab()
                .size(FabElementBuilder.PlainFabElementBuilder.Size.Large)
                .icon("favorite")
                .ariaLabel("즐겨찾기")
                .element();
        largeExample.addInteractiveDemo(largeFab, false);
        assertEquals("large FAB: size large", "large", largeFab.size);
    }

    private static void testFabVariants() {
        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("FAB Variants").element());

        // Surface (default)
        var surfaceExample = addExampleCode(variantsSection,
            "📘 Surface FAB (기본)",
            "기본 surface 색상의 FAB입니다.",
            """
            var fab = button().fab()
                .variant(FabElementBuilder.Variant.Surface)
                .icon("home")
                .ariaLabel("홈")
                .element();
            """);
        var surfaceFab = button().fab()
                .variant(FabElementBuilder.Variant.Surface)
                .icon("home")
                .ariaLabel("홈")
                .element();
        surfaceExample.addInteractiveDemo(surfaceFab, false);
        assertEquals("surface FAB: variant surface", "surface", surfaceFab.variant);

        // Primary
        var primaryExample = addExampleCode(variantsSection,
            "📘 Primary FAB (주요 액션)",
            "Primary 색상의 FAB입니다. 가장 중요한 액션에 사용합니다.",
            """
            var fab = button().fab()
                .variant(FabElementBuilder.Variant.Primary)
                .icon("add")
                .ariaLabel("추가")
                .element();
            """);
        var primaryFab = button().fab()
                .variant(FabElementBuilder.Variant.Primary)
                .icon("add")
                .ariaLabel("추가")
                .element();
        primaryExample.addInteractiveDemo(primaryFab, false);
        assertEquals("primary FAB: variant primary", "primary", primaryFab.variant);

        // Secondary
        var secondaryExample = addExampleCode(variantsSection,
            "📘 Secondary FAB (보조 액션)",
            "Secondary 색상의 FAB입니다.",
            """
            var fab = button().fab()
                .variant(FabElementBuilder.Variant.Secondary)
                .icon("edit")
                .ariaLabel("편집")
                .element();
            """);
        var secondaryFab = button().fab()
                .variant(FabElementBuilder.Variant.Secondary)
                .icon("edit")
                .ariaLabel("편집")
                .element();
        secondaryExample.addInteractiveDemo(secondaryFab, false);
        assertEquals("secondary FAB: variant secondary", "secondary", secondaryFab.variant);

        // Tertiary
        var tertiaryExample = addExampleCode(variantsSection,
            "📘 Tertiary FAB (3차 액션)",
            "Tertiary 색상의 FAB입니다.",
            """
            var fab = button().fab()
                .variant(FabElementBuilder.Variant.Tertiary)
                .icon("share")
                .ariaLabel("공유")
                .element();
            """);
        var tertiaryFab = button().fab()
                .variant(FabElementBuilder.Variant.Tertiary)
                .icon("share")
                .ariaLabel("공유")
                .element();
        tertiaryExample.addInteractiveDemo(tertiaryFab, false);
        assertEquals("tertiary FAB: variant tertiary", "tertiary", tertiaryFab.variant);
    }

    private static void testExtendedFab() {
        var extendedSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(extendedSection);

        extendedSection.appendChild(h(3).text("Extended FAB").element());

        // Extended FAB with icon and label
        var extendedExample = addExampleCode(extendedSection,
            "📘 Extended FAB (확장형 FAB)",
            "아이콘과 텍스트 레이블이 함께 표시되는 FAB입니다.",
            """
            var fab = button().fab()
                .icon("navigation")
                .label("Navigate")
                .ariaLabel("내비게이션")
                .element();
            """);
        var extendedFab = button().fab()
                .icon("navigation")
                .label("Navigate")
                .ariaLabel("내비게이션")
                .element();
        extendedExample.addInteractiveDemo(extendedFab, false);
        assertEquals("extended FAB: label 존재", "Navigate", extendedFab.label);

        // Extended FAB without icon
        var labelOnlyExample = addExampleCode(extendedSection,
            "📘 Label Only Extended FAB (레이블만 있는 확장형 FAB)",
            "아이콘 없이 레이블만 표시되는 FAB입니다.",
            """
            var fab = button().fab()
                .label("Compose")
                .ariaLabel("새 글 작성")
                .element();
            """);
        var labelOnlyFab = button().fab()
                .label("Compose")
                .ariaLabel("새 글 작성")
                .element();
        labelOnlyExample.addInteractiveDemo(labelOnlyFab, false);
        assertEquals("label-only FAB: label 존재", "Compose", labelOnlyFab.label);

        // Extended FAB with variant
        var styledExtendedExample = addExampleCode(extendedSection,
            "📘 Styled Extended FAB (스타일이 적용된 확장형 FAB)",
            "Primary 색상의 확장형 FAB입니다.",
            """
            var fab = button().fab()
                .variant(FabElementBuilder.Variant.Primary)
                .size(PlainFabElementBuilder.Size.Large)
                .icon("add")
                .label("Create")
                .ariaLabel("새로 만들기")
                .element();
            """);
        var styledExtendedFab = button().fab()
                .variant(FabElementBuilder.Variant.Primary)
                .size(FabElementBuilder.PlainFabElementBuilder.Size.Large)
                .icon("add")
                .label("Create")
                .ariaLabel("새로 만들기")
                .element();
        styledExtendedExample.addInteractiveDemo(styledExtendedFab, false);
        assertEquals("styled extended FAB: variant primary", "primary", styledExtendedFab.variant);
        assertEquals("styled extended FAB: size large", "large", styledExtendedFab.size);
    }

    private static void testBrandedFab() {
        var brandedSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(brandedSection);

        brandedSection.appendChild(h(3).text("Branded FAB").element());

        // Medium Branded FAB
        var mediumBrandedExample = addExampleCode(brandedSection,
            "📘 Medium Branded FAB (중간 브랜드 FAB)",
            "브랜드 로고가 있는 중간 크기 FAB입니다.",
            """
            var fab = button().fab().branded()
                .size(BrandedFabElementBuilder.Size.Medium)
                .icon("local_fire_department")
                .ariaLabel("브랜드 액션")
                .element();
            """);
        var mediumBrandedFab = button().fab().branded()
                .size(FabElementBuilder.BrandedFabElementBuilder.Size.Medium)
                .icon("local_fire_department")
                .ariaLabel("브랜드 액션")
                .element();
        mediumBrandedExample.addInteractiveDemo(mediumBrandedFab, false);
        assertEquals("medium branded FAB: md-branded-fab", "MD-BRANDED-FAB", mediumBrandedFab.tagName);
        assertEquals("medium branded FAB: size medium", "medium", mediumBrandedFab.size);

        // Large Branded FAB
        var largeBrandedExample = addExampleCode(brandedSection,
            "📘 Large Branded FAB (큰 브랜드 FAB)",
            "브랜드 로고가 있는 큰 크기 FAB입니다.",
            """
            var fab = button().fab().branded()
                .size(BrandedFabElementBuilder.Size.Large)
                .icon("local_fire_department")
                .ariaLabel("브랜드 액션")
                .element();
            """);
        var largeBrandedFab = button().fab().branded()
                .size(FabElementBuilder.BrandedFabElementBuilder.Size.Large)
                .icon("local_fire_department")
                .ariaLabel("브랜드 액션")
                .element();
        largeBrandedExample.addInteractiveDemo(largeBrandedFab, false);
        assertEquals("large branded FAB: size large", "large", largeBrandedFab.size);

        // Extended Branded FAB
        var extendedBrandedExample = addExampleCode(brandedSection,
            "📘 Extended Branded FAB (확장형 브랜드 FAB)",
            "레이블이 있는 확장형 브랜드 FAB입니다.",
            """
            var fab = button().fab().branded()
                .icon("local_fire_department")
                .label("Brand")
                .ariaLabel("브랜드 홍보")
                .element();
            """);
        var extendedBrandedFab = button().fab().branded()
                .icon("local_fire_department")
                .label("Brand")
                .ariaLabel("브랜드 홍보")
                .element();
        extendedBrandedExample.addInteractiveDemo(extendedBrandedFab, false);
        assertEquals("extended branded FAB: label 존재", "Brand", extendedBrandedFab.label);
    }

    private static void testFabProperties() {
        var propsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propsSection);

        propsSection.appendChild(h(3).text("FAB Properties").element());

        // Lowered elevation
        var loweredExample = addExampleCode(propsSection,
            "📘 Lowered FAB (낮은 높이)",
            "elevation이 낮은 FAB입니다. 덜 강조된 액션에 사용합니다.",
            """
            var fab = button().fab()
                .icon("mail")
                .lowered(true)
                .ariaLabel("메일")
                .element();
            """);
        var loweredFab = button().fab()
                .icon("mail")
                .lowered(true)
                .ariaLabel("메일")
                .element();
        loweredExample.addInteractiveDemo(loweredFab, false);
        assertTrue("lowered FAB: lowered true", loweredFab.lowered);

        // Click event
        var clickExample = addExampleCode(propsSection,
            "📘 FAB Click Event (클릭 이벤트)",
            "클릭 이벤트를 처리하는 FAB입니다.",
            """
            var fab = button().fab()
                .icon("notifications")
                .ariaLabel("알림")
                .onClick(e -> console.log("FAB clicked"))
                .element();
            """);
        var clickableFab = button().fab()
                .icon("notifications")
                .ariaLabel("알림")
                .element();
        var clickState = clickExample.addInteractiveDemo(clickableFab);
        clickState.textContent = "클릭 횟수: 0";
        var clickCount = new AtomicInteger();
        clickableFab.addEventListener("click", evt -> clickState.textContent = "클릭 횟수: " + clickCount.incrementAndGet());

        // Touch target (for small FAB)
        var touchTargetExample = addExampleCode(propsSection,
            "📘 Touch Target (터치 영역)",
            "작은 FAB의 터치 영역을 제거합니다.",
            """
            var fab = button().fab()
                .size(PlainFabElementBuilder.Size.Small)
                .icon("close")
                .touchTarget("none")
                .ariaLabel("닫기")
                .element();
            """);
        var touchTargetFab = button().fab()
                .size(FabElementBuilder.PlainFabElementBuilder.Size.Small)
                .icon("close")
                .touchTarget("none")
                .ariaLabel("닫기")
                .element();
        touchTargetExample.addInteractiveDemo(touchTargetFab, false);
        assertEquals("touch target FAB: touchTarget none", "none", touchTargetFab.touchTarget);

        // Accessibility
        var a11yExample = addExampleCode(propsSection,
            "📘 Accessibility (접근성)",
            "FAB는 반드시 aria-label을 제공해야 합니다.",
            """
            var fab = button().fab()
                .icon("info")
                .ariaLabel("정보 보기")
                .element();
            """);
        var a11yFab = button().fab()
                .icon("info")
                .ariaLabel("정보 보기")
                .element();
        a11yExample.addInteractiveDemo(a11yFab, false);
        assertEquals("a11y FAB: aria-label", "정보 보기", a11yFab.getAttribute("aria-label"));
    }
}
