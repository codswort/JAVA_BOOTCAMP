package edu.school21.processor;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;


@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_20)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for( final Element element: roundEnv.getElementsAnnotatedWith(HtmlForm.class) ) {
            if (element.getKind() != ElementKind.CLASS) continue;
            try {
                generateHtmlForm(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private void generateHtmlForm(Element element) throws IOException {
        HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
        StringBuilder html = new StringBuilder();
        html.append("<form action=\"").append(htmlForm.action())
                .append("\" method=\"").append(htmlForm.method())
                .append("\">\n");

        for (Element enclosed : element.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.FIELD && enclosed.getAnnotation(HtmlInput.class) != null) {
                generateHtmlInput(enclosed, html);
            }
        }
        html.append("\t<input type=\"submit\" value=\"Send\">\n");
        html.append("</form>");
        generateHtmlFile(element, html);
    }

    private void generateHtmlInput(Element enclosed, StringBuilder html) {
        HtmlInput htmlInput = enclosed.getAnnotation(HtmlInput.class);
        html.append("\t<input type=\"").append(htmlInput.type())
                .append("\" name=\"").append(htmlInput.name()).append("\"");
        if (!htmlInput.placeholder().isEmpty()) {
            html.append(" placeholder=\"").append(htmlInput.placeholder()).append("\"");
        }
        html.append(">\n");
    }

    private void generateHtmlFile(Element element, StringBuilder html) {
        try {
            String className = element.getSimpleName() + ".html";
            Filer filer = processingEnv.getFiler();
            FileObject fileObject = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", className);
            try (Writer writer = fileObject.openWriter()) {
                writer.write(html.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
