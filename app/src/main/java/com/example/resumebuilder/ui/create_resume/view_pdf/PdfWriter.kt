package com.example.resumebuilder.ui.create_resume.view_pdf

import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfWriter

class PdfWriter {

    private var docWriter: PdfWriter? = null

    var bfBold: BaseFont? = null
    var bfNormal: BaseFont? = null
    var bfItalic: BaseFont? = null

    fun addAdditionalExperience(
        cb: PdfContentByte,
        black: BaseColor,
        font: Font?
    ) {
        createHeadings(cb, 200, 250, "ADDITIONAL EXPERIENCE", 12, "bold", black)

        createHeadings(cb, 210, 230, PdfStaticValues.addworkTitle1, 11, "bold", black)
        createHeadings(cb, 210, 210, PdfStaticValues.addworkCom1, 10, "italic", black)

        val rect6 = Rectangle(210F, 210F, 580F, 190F)
        val ct6 = ColumnText(docWriter!!.directContent)
        ct6.setSimpleColumn(rect6)
        ct6.addElement(Paragraph(PdfStaticValues.addwork1_1, font))
        ct6.go()

        createHeadings(cb, 210, 170, PdfStaticValues.addworkTitle2, 11, "bold", black)
        createHeadings(cb, 210, 150, PdfStaticValues.addworkCom2, 10, "italic", black)

        val rect7 = Rectangle(210F, 150F, 580F, 130F)
        val ct7 = ColumnText(docWriter!!.directContent)
        ct7.setSimpleColumn(rect7)
        ct7.addElement(Paragraph(PdfStaticValues.addwork2_1, font))
        ct7.go()


        val rect8 = Rectangle(210F, 130F, 580F, 110F)
        val ct8 = ColumnText(docWriter!!.directContent)
        ct8.setSimpleColumn(rect8)
        ct8.addElement(Paragraph(PdfStaticValues.addwork2_2, font))
        ct8.go()
    }

    fun addProfessionalExperienceSection(
        cb: PdfContentByte,
        black: BaseColor,
        font: Font?
    ) {
        createHeadings(cb, 200, 490, "PROFESSIONAL EXPERIENCE", 12, "bold", black)
        createHeadings(cb, 210, 470, PdfStaticValues.workTitle1, 11, "bold", black)
        createHeadings(cb, 210, 450, PdfStaticValues.workCom1, 10, "italic", black)

        val rect1 = Rectangle(210F, 450F, 580F, 380F)
        val ct1 = ColumnText(docWriter!!.directContent)

        ct1.setSimpleColumn(rect1)
        ct1.addElement(Paragraph(PdfStaticValues.work1_1, font))
        ct1.go()


        val rect2 = Rectangle(210F, 400F, 580F, 380F)
        val ct2 = ColumnText(docWriter!!.directContent)
        ct2.setSimpleColumn(rect2)
        ct2.addElement(Paragraph(PdfStaticValues.work1_2, font))
        ct2.go()

        val rect3 = Rectangle(210F, 380F, 580F, 360F)
        val ct3 = ColumnText(docWriter!!.directContent)
        ct3.setSimpleColumn(rect3)
        ct3.addElement(Paragraph(PdfStaticValues.work1_3, font))
        ct3.go()


        createHeadings(cb, 210, 340, PdfStaticValues.workTitle2, 11, "bold", black)
        createHeadings(cb, 210, 320, PdfStaticValues.workCom2, 10, "italic", black)

        val rect4 = Rectangle(210F, 320F, 580F, 300F)
        val ct4 = ColumnText(docWriter!!.directContent)
        ct4.setSimpleColumn(rect4)
        ct4.addElement(Paragraph(PdfStaticValues.work2_1, font))
        ct4.go()

        val rect5 = Rectangle(210F, 300F, 580F, 280F)
        val ct5 = ColumnText(docWriter!!.directContent)
        ct5.setSimpleColumn(rect5)
        ct5.addElement(Paragraph(PdfStaticValues.work2_2, font))
        ct5.go()
    }

    fun addProfileSection(
        cb: PdfContentByte,
        black: BaseColor,
        font: Font?
    ) {
        createHeadings(cb, 200, 570, "PROFILE", 12, "bold", black)

        val rect = Rectangle(210F, 570F, 580F, 460F)
        val ct = ColumnText(docWriter!!.directContent)

        ct.setSimpleColumn(rect)

        ct.addElement(Paragraph(PdfStaticValues.about, font))
        try {
            ct.go()
        } catch (e: DocumentException) {
            e.printStackTrace()
        }
    }

    fun addExpertiseSection(
        cb: PdfContentByte,
        black: BaseColor
    ) {
        createHeadings(cb, 30, 310, "EXPERTISE", 12, "bold", black);

        createHeadings(cb, 40, 290, PdfStaticValues.expe1, 10, "normal", black);
        createHeadings(cb, 40, 270, PdfStaticValues.expe2, 10, "normal", black);
        createHeadings(cb, 40, 250, PdfStaticValues.expe3, 10, "normal", black);
        createHeadings(cb, 40, 230, PdfStaticValues.expe4, 10, "normal", black);
        createHeadings(cb, 40, 210, PdfStaticValues.expe5, 10, "normal", black);
    }

    fun addNameSection(
        cb: PdfContentByte,
        white: BaseColor
    ) {
        createHeadings(cb, 170, 750, PdfStaticValues.name, 40, "bold", white)
        createHeadings(cb, 190, 680, PdfStaticValues.title, 20, "normal", white)
    }

    fun addContactsSection(
        cb: PdfContentByte,
        black: BaseColor,
        myColor: BaseColor
    ) {
        createHeadings(cb, 30, 570, "CONTACT", 12, "bold", black)

        createHeadings(cb, 40, 550, PdfStaticValues.phone, 10, "normal", black)
        createHeadings(cb, 40, 530, PdfStaticValues.email, 10, "normal", black)
        createHeadings(cb, 40, 510, PdfStaticValues.place, 10, "normal", black)

        val line1 = Rectangle(30F, 490F, 180F, 491F)
        line1.backgroundColor = myColor
        cb.rectangle(line1)
    }

    fun addSkillsSection(
        cb: PdfContentByte,
        black: BaseColor,
        myColor: BaseColor
    ) {
        createHeadings(cb, 30, 470, "SKILLS", 12, "bold", black)

        createHeadings(cb, 40, 450, PdfStaticValues.skill1, 10, "normal", black)
        createHeadings(cb, 40, 430, PdfStaticValues.skill2, 10, "normal", black)
        createHeadings(cb, 40, 410, PdfStaticValues.skill3, 10, "normal", black)
        createHeadings(cb, 40, 390, PdfStaticValues.skill4, 10, "normal", black)
        createHeadings(cb, 40, 370, PdfStaticValues.skill5, 10, "normal", black)
        createHeadings(cb, 40, 350, PdfStaticValues.skill6, 10, "normal", black)

        val line2 = Rectangle(30F, 330F, 180F, 331F)
        line2.backgroundColor = myColor
        cb.rectangle(line2)
    }

    fun createHeadings(
        pdfContentByte: PdfContentByte,
        x: Int,
        y: Int,
        text: String,
        size: Int,
        fontStyle: String,
        color: BaseColor
    ) {
        pdfContentByte.beginText()

        when (fontStyle) {
            "normal" -> pdfContentByte.setFontAndSize(bfNormal, size.toFloat())
            "bold" -> pdfContentByte.setFontAndSize(bfBold, size.toFloat())
            "italic" -> pdfContentByte.setFontAndSize(bfItalic, size.toFloat())
        }

        pdfContentByte.setColorFill(color)
        pdfContentByte.setTextMatrix(x.toFloat(), y.toFloat())
        pdfContentByte.showText(text)
        pdfContentByte.endText()
    }

}