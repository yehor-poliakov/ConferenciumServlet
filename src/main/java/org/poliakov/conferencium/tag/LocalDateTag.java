package org.poliakov.conferencium.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTag extends SimpleTagSupport {
    private LocalDate date;
    private String locale;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();

        String languageTag = locale.equals("ua") ? "uk" : locale;
        Locale loc = Locale.forLanguageTag(languageTag);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy", loc);
        String formattedDate = date.format(formatter);

        out.println(formattedDate);
    }
}
