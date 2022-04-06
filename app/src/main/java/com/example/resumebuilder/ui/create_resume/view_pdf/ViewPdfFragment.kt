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

//        val rectangle1 = Rectangle(0F, 1000F, 800F, 600F)
//        val myColor = BaseColor(42, 42, 42)
//        rectangle1.backgroundColor = myColor
//        cb.rectangle(rectangle1)

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

        val contactDetails = PdfWriteHelper().getContactDetails(
            resume = mViewModel.resume,
            pdfContentByte = cb,
            contentFont = bfNormal!!
        )
        document.add(contactDetails)

        val educationDetails = PdfWriteHelper().getEducationDetails(
            educationDetails = mViewModel.resume.educationDetails,
            pdfContentByte = cb,
            contentFont = bfNormal!!
        )
        document.add(educationDetails)

        val skills = PdfWriteHelper().getSkills(
            skills = mViewModel.resume.skills,
            pdfContentByte = cb,
            contentFont = bfNormal!!
        )
        document.add(skills)

        val workSummary = PdfWriteHelper().getWorkSummary(
            workSummary = mViewModel.resume.workSummary,
            pdfContentByte = cb,
            contentFont = bfNormal!!
        )
        document.add(workSummary)

        val projects = PdfWriteHelper().getProjects(
            projects = mViewModel.resume.projects,
            pdfContentByte = cb,
            contentFont = bfNormal!!
        )
        document.add(projects)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}