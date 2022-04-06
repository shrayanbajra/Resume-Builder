package com.example.resumebuilder.ui.create_resume.view_pdf

import android.content.Intent
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
import com.example.resumebuilder.utils.Constants
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import timber.log.Timber
import java.io.*


class ViewPdfFragment : Fragment() {

    private var _binding: FragmentViewPdfBinding? = null
    private val mBinding get() = _binding!!

    private var pdfFile: File? = null
    private var docWriter: PdfWriter? = null

    private val headingFont: Font = FontFactory.getFont(
        "res/font/sf_pro_display_medium.OTF",
        BaseFont.IDENTITY_H,
        BaseFont.EMBEDDED,
        16f,
        Font.NORMAL,
        BaseColor.BLACK
    )

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
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val extension = MimeTypeMap.getFileExtensionFromUrl(pdfFile?.name)
        var mimeType = mimeTypeMap.getMimeTypeFromExtension(extension)

        if (mimeType == null) mimeType = "*/*"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName.toString() + ".provider",
            pdfFile!!
        )
        intent.setDataAndType(fileUri, mimeType)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        Timber.d("URI -> $fileUri")
        mBinding.pdfView.fromFile(pdfFile)
            .swipeHorizontal(false)
            .load()

//        requireContext().startActivity(intent)

    }

    fun proceedToCreatingPdf(): Unit? {

        try {

            createResume()

        } catch (e: FileNotFoundException) {

            e.printStackTrace()

        } catch (e: DocumentException) {

            e.printStackTrace()

        }
        return null

    }

    @Throws(FileNotFoundException::class, DocumentException::class)
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

        document.apply {
            addNameSection()
            addContactsSection()
            addSkillsSection()
            addWorkSummarySection()
            addEducationSection()
            addProjectsSection()
        }

        try {
            document.close()
            output!!.flush()
            output.close()
            docWriter!!.close()

            openPdf()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun Document.addNameSection() {

        mViewModel.resume.profilePhoto?.let { photo ->
            val image = Image.getInstance(photo)
            image.scaleToFit(100f, 100f)
            this.add(image)
        }

        val font = FontFactory.getFont(
            "res/font/sf_pro_display_medium.OTF",
            BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED,
            20f,
            Font.NORMAL,
            BaseColor.BLACK
        )
        val fullName = Paragraph(mViewModel.resume.fullName, font)
        this.add(fullName)

        this.add(Paragraph(Constants.NEW_LINE))
    }

    private fun Document.addContactsSection() {
        val contactDetailsHeader = Paragraph("Contact", headingFont)
        this.add(contactDetailsHeader)

        val contactDetails = PdfWriteHelper().getContactDetails(resume = mViewModel.resume)
        this.add(contactDetails)
    }

    private fun Document.addEducationSection() {
        val educationHeader = Paragraph("Education", headingFont)
        this.add(educationHeader)

        val educationDetails = PdfWriteHelper().getEducationDetails(
            educationDetails = mViewModel.resume.educationDetails
        )
        this.add(educationDetails)
    }

    private fun Document.addSkillsSection() {
        val skillsHeader = Paragraph(Constants.NEW_LINE + "Skills", headingFont)
        this.add(skillsHeader)

        val skills = PdfWriteHelper().getSkills(skills = mViewModel.resume.skills)
        this.add(skills)
    }

    private fun Document.addWorkSummarySection() {
        val workSummaryHeader = Paragraph(Constants.NEW_LINE + "Work Summary", headingFont)
        this.add(workSummaryHeader)

        val workSummary = PdfWriteHelper().getWorkSummary(
            workSummary = mViewModel.resume.workSummary
        )
        this.add(workSummary)
    }

    private fun Document.addProjectsSection() {
        val projectsHeader = Paragraph("Projects", headingFont)
        this.add(projectsHeader)

        val projects = PdfWriteHelper().getProjects(projects = mViewModel.resume.projects)
        this.add(projects)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}