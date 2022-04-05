package com.example.resumebuilder.ui.create_resume.view_pdf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.resumebuilder.R
import com.example.resumebuilder.databinding.FragmentViewPdfBinding
import com.example.resumebuilder.ui.ResumeViewModel
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfWriter
import timber.log.Timber
import java.io.*


class ViewPdfFragment : Fragment() {

    private var _binding: FragmentViewPdfBinding? = null
    private val mBinding get() = _binding!!

    private var pdfFile: File? = null
    private var docWriter: PdfWriter? = null
    var bfBold: BaseFont? = null
    var bfNormal: BaseFont? = null
    var bfItalic: BaseFont? = null

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPdfBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnDone.setOnClickListener {

            findNavController().navigate(R.id.action_viewPdfFragment_to_resumesFragment)

        }

        proceedToCreatingPdf()

    }

    private fun openPdf() {
        val map = MimeTypeMap.getSingleton()
        val ext = MimeTypeMap.getFileExtensionFromUrl(pdfFile?.name)
        var type = map.getMimeTypeFromExtension(ext)

        if (type == null) type = "*/*"

        val intent = Intent(Intent.ACTION_VIEW)
        val data: Uri = Uri.fromFile(pdfFile)

        intent.setDataAndType(data, type)
        startActivity(intent)
    }

    private fun openPdfWithNewStyle() {
        val map = MimeTypeMap.getSingleton()
        val ext = MimeTypeMap.getFileExtensionFromUrl(pdfFile?.name)
        var mimeType = map.getMimeTypeFromExtension(ext)

        if (mimeType == null) mimeType = "*/*"

        val install = Intent(Intent.ACTION_VIEW)
        install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName.toString() + ".provider",
            pdfFile!!
        )
        install.setDataAndType(fileUri, mimeType)
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        Timber.d("URI -> $fileUri")

        requireContext().startActivity(install)

    }

    fun proceedToCreatingPdf(): Unit? {

        try {

            createPdfWrapper()

        } catch (e: FileNotFoundException) {

            e.printStackTrace()

        } catch (e: DocumentException) {

            e.printStackTrace()

        }
        return null

    }

    @Throws(FileNotFoundException::class, DocumentException::class)
    fun createPdfWrapper() {

        createResume()

    }

    private fun initializeFonts() {
        val font: Font = FontFactory.getFont(
            "res/font/sf_pro_display_medium.OTF",
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK
        )
        bfNormal = font.getBaseFont()
        val font1: Font = FontFactory.getFont(
            "res/font/sf_pro_display_bold.OTF",
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK
        )
        bfBold = font1.getBaseFont()
        val font2: Font = FontFactory.getFont(
            "res/font/sf_pro_display_black_italic.OTF",
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK
        )
        bfItalic = font2.getBaseFont()
    }

    private fun createHeadings(
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

    @Throws(DocumentException::class)
    private fun createResume() {
        val docsFolder = File(requireContext().getExternalFilesDir("Resumes"), "PDFs")
        if (!docsFolder.exists()) {
            docsFolder.mkdir()
            docsFolder.setExecutable(true)
        }
        pdfFile = File(docsFolder, "${mViewModel.resume.fileName}.pdf")
        var output: OutputStream? = null
        try {
            output = FileOutputStream(pdfFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val document = Document()
        try {
            docWriter = PdfWriter.getInstance(document, output)
        } catch (e: DocumentException) {
            e.printStackTrace()
        }
        document.open()
        initializeFonts()
        val cb = docWriter!!.directContent

        val rectangle1 = Rectangle(0F, 1000F, 800F, 600F)
        val myColor = BaseColor(42, 42, 42)
        rectangle1.backgroundColor = myColor
        cb.rectangle(rectangle1)

        val black = BaseColor(0, 0, 0)
        val white = BaseColor(255, 255, 255)
        val font = FontFactory.getFont(
            "res/font/sf_pro_display_medium.OTF",
            BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED,
            10f,
            Font.NORMAL,
            BaseColor.BLACK
        )

        addNameSection(cb, white)

        addContactsSection(cb, black, myColor)

        addSkillsSection(cb, black, myColor)

        addExpertiseSection(cb, black)

        addProfileSection(cb, black, font)

        addProfessionalExperienceSection(cb, black, font)

        addAdditionalExperience(cb, black, font)

        try {
            document.close()
            output!!.flush()
            output.close()
            docWriter!!.close()

            openPdfWithNewStyle()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun addAdditionalExperience(
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

    private fun addProfessionalExperienceSection(
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

    private fun addProfileSection(
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

    private fun addExpertiseSection(
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

    private fun addNameSection(
        cb: PdfContentByte,
        white: BaseColor
    ) {
        mViewModel.resume.fullName?.let {
            createHeadings(cb, 170, 750, it, 40, "bold", white)
        }
        createHeadings(cb, 190, 680, PdfStaticValues.title, 20, "normal", white)
    }

    private fun addContactsSection(
        cb: PdfContentByte,
        black: BaseColor,
        myColor: BaseColor
    ) {
        createHeadings(cb, 30, 570, "CONTACT", 12, "bold", black)

        mViewModel.resume.apply {
            mobileNumber?.let { createHeadings(cb, 40, 550, it, 10, "normal", black) }
            emailAddress?.let { createHeadings(cb, 40, 530, it, 10, "normal", black) }
            address?.let { createHeadings(cb, 40, 510, it, 10, "normal", black) }
        }

        val line1 = Rectangle(30F, 490F, 180F, 491F)
        line1.backgroundColor = myColor
        cb.rectangle(line1)
    }

    private fun addSkillsSection(
        cb: PdfContentByte,
        black: BaseColor,
        myColor: BaseColor
    ) {
        createHeadings(cb, 30, 470, "SKILLS", 12, "bold", black)

        val x = 40
        var y = 350
        mViewModel.resume.skills?.forEach { skill ->

            skill.title?.let { createHeadings(cb, x, y, it, 10, "normal", black) }
            y += 20

        }


//        createHeadings(cb, 40, 430, PdfStaticValues.skill2, 10, "normal", black)
//        createHeadings(cb, 40, 410, PdfStaticValues.skill3, 10, "normal", black)
//        createHeadings(cb, 40, 390, PdfStaticValues.skill4, 10, "normal", black)
//        createHeadings(cb, 40, 370, PdfStaticValues.skill5, 10, "normal", black)
//        createHeadings(cb, 40, 350, PdfStaticValues.skill6, 10, "normal", black)

        val line2 = Rectangle(30F, 330F, 180F, 331F)
        line2.backgroundColor = myColor
        cb.rectangle(line2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}