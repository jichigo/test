package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.date.DateFactory;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.sequencer.Sequencer;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import xxxxxx.yyyyyy.zzzzzz.app.sample.ArticleForm.ArticleDelete;
import xxxxxx.yyyyyy.zzzzzz.app.sample.ArticleForm.ArticleLoad;
import xxxxxx.yyyyyy.zzzzzz.app.sample.ArticleForm.ArticleUpdate;
import xxxxxx.yyyyyy.zzzzzz.app.sample.FileUploadForm.Upload;
import xxxxxx.yyyyyy.zzzzzz.domain.model.Article;
import xxxxxx.yyyyyy.zzzzzz.domain.model.ArticleClass;
import xxxxxx.yyyyyy.zzzzzz.domain.service.ArticleSearchCriteria;
import xxxxxx.yyyyyy.zzzzzz.domain.service.ArticleService;
import xxxxxx.yyyyyy.zzzzzz.domain.service.FileUploadService;
import xxxxxx.yyyyyy.zzzzzz.domain.service.UploadFileInfo;

@TransactionTokenCheck("article")
@RequestMapping("article")
@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory
            .getLogger(ArticleController.class);

    @Value("${upload.allowableFileSize}")
    private int uploadAllowableFileSize;

    @Inject
    private Mapper beanMapper;

    @Inject
    private DateFactory dateFactory;

    @Inject
    private ArticleService articleService;

    @Value("${app.upload.temporaryDirectory}")
    private File uploadTemporaryDirectory;

    @Value("${app.upload.directory}")
    private File uploadDirectory;

    @Inject
    private FileUploadService fileUploadService;

    @Inject
    @Named("articleIdSequencer")
    private Sequencer<String> articleIdSequencer;

    @ModelAttribute
    public ArticleSearchCriteriaForm setUpArticleSearchCriteriaForm() {
        ArticleSearchCriteriaForm form = new ArticleSearchCriteriaForm();
        return form;
    }

    @ModelAttribute
    public FilesUploadForm setUpUploadFilesForm() {
        FilesUploadForm form = new FilesUploadForm();
        return form;
    }

    @ModelAttribute
    public FilesUploadForm2 setUpUploadFilesForm2() {
        FilesUploadForm2 form = new FilesUploadForm2();
        return form;
    }

    @RequestMapping(value = "list", params = { "form" })
    public String list() {
        return "article/list";

    }

    @Transactional
    @RequestMapping("search")
    public String search(@Validated ArticleSearchCriteriaForm form,
            BindingResult result,
            /*
             * @PageableDefault(size = 50)
             * @SortDefaults( {
             * @SortDefault(sort = "publishedDate", direction = Direction.DESC),
             * @SortDefault(sort = "articleId") })
             */Pageable pageable, Model model) {

        // String articleId = articleIdSequencer.getNext();
        // model.addAttribute("articleId", articleId);

        return list(form, result, pageable, model);
    }

    /*
     * @PageableDefault( size = 11, direction = Direction.DESC, sort = { "publishedDate", "articleId" } )
     */
    /*
     * @SortDefaults({
     * @SortDefault(sort = "publishedDate", direction = Direction.DESC) })
     */
    @RequestMapping("list")
    public String list(@Validated ArticleSearchCriteriaForm form,
            BindingResult result, Pageable pageable, Model model) {

        if (result.hasErrors()) {
            logger.debug("validation of form is failed. detail : {}", result);
            return "article/list";
        }

        if (!StringUtils.hasLength(form.getWord())) {
            return "article/list";
        }

        ArticleSearchCriteria criteria = beanMapper.map(form,
                ArticleSearchCriteria.class);

        long startTime = System.nanoTime();

        Page<Article> page = articleService.searchArticle(criteria, pageable);

        long endTime = System.nanoTime();

        model.addAttribute("page", page);
        model.addAttribute("searchedNanoTime", endTime - startTime);

        if (page.getTotalPages() == 0) {
            ResultMessage message = ResultMessage
                    .fromText("Did not found article that match the criteria.");
            model.addAttribute(ResultMessages.info().add(message));
        }

        return "article/list";

    }

    @ModelAttribute
    public FileUploadForm setFileUploadForm() {
        return new FileUploadForm();
    }

    @TransactionTokenCheck(value = "upload", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String uploadForm() {
        return "article/uploadForm";
    }

    @TransactionTokenCheck(value = "upload")
    @RequestMapping(value = "upload", method = RequestMethod.POST, params = "redo")
    public String uploadRedo(FileUploadForm form) throws IOException {

        return "article/uploadForm";
    }

    @TransactionTokenCheck(value = "upload")
    @RequestMapping(value = "upload", method = RequestMethod.POST, params = "confirm")
    public String uploadConfirm(
            @Validated({ Upload.class, Default.class }) FileUploadForm form,
            BindingResult result, RedirectAttributes redirectAttributes) throws IOException {

        if (result.hasErrors()) {
            return "article/uploadForm";
        }

        MultipartFile uploadFile = form.getFile();

        String uploadTemporaryFileId = UUID.randomUUID().toString();
        File uploadTemporaryFile = new File(uploadTemporaryDirectory, uploadTemporaryFileId);

        uploadFile.transferTo(uploadTemporaryFile);
        form.setUploadTemporaryFileId(uploadTemporaryFileId);
        form.setFileName(uploadFile.getOriginalFilename());

        redirectAttributes.addFlashAttribute(form);

        return "article/uploadConfirm";
    }

    @TransactionTokenCheck(value = "upload")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@Validated FileUploadForm form, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "article/uploadForm";
        }

        UploadFileInfo uploadTemporaryFileInfo = new UploadFileInfo(form
                .getUploadTemporaryFileId(), form.getFileName(), form
                .getDescription());

        UploadFileInfo uploadedFileInfo = fileUploadService
                .upload(uploadTemporaryFileInfo);

        redirectAttributes.addFlashAttribute(uploadedFileInfo);

        redirectAttributes.addFlashAttribute(ResultMessages.success().add(
                "i.xx.fw.0001"));

        return "redirect:/article/upload?complete";
    }

    @RequestMapping(value = "upload", method = RequestMethod.GET, params = "complete")
    public String uploadComplate() {
        return "article/uploadComplete";
    }

    @RequestMapping(value = "uploadFiles", method = RequestMethod.POST)
    public String uploadFiles(@Validated FilesUploadForm form,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "article/uploadForm";
        }

        for (FileUploadForm fileUploadForm : form.getFileUploadForms()) {

            // omit processing of upload.

        }

        return "redirect:/article/upload";
    }

    @RequestMapping(value = "uploadFiles2", method = RequestMethod.POST)
    public String uploadFiles2(@Validated FilesUploadForm2 form,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "article/uploadForm";
        }

        for (MultipartFile file : form.getFiles()) {

            // omit processing of upload.
            System.out.println(file.getOriginalFilename());

        }

        return "redirect:/article/upload";
    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String createForm(ArticleForm form) {
        return "article/saveForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, params = "load")
    public String load(@Validated({ ArticleLoad.class }) ArticleForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/saveForm";
        }
        Article createdArticle = articleService.find(form.getArticleId());
        beanMapper.map(createdArticle, form);
        return "article/saveForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, params = "persist")
    public String create(@Validated ArticleForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "article/saveForm";
        }
        Article inputtedArticle = beanMapper.map(form, Article.class);
        inputtedArticle.setArticleClass(ArticleClass.getArticleClass(form
                .getArticleClassId()));

        Article createdArticle = articleService.create(inputtedArticle);

        form.setArticleId(createdArticle.getArticleId());
        model.addAttribute(ResultMessages.success().add(
                ResultMessage.fromText(String.format(
                        "New Article created. Id is '%d'.", createdArticle
                                .getArticleId()))));

        return "article/saveForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, params = "merge")
    public String update(
            @Validated({ Default.class, ArticleUpdate.class }) ArticleForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "article/saveForm";
        }

        Article inputtedArticle = beanMapper.map(form, Article.class);
        inputtedArticle.setArticleClass(ArticleClass.getArticleClass(form
                .getArticleClassId()));

        Article createdArticle = articleService.update(inputtedArticle);

        model.addAttribute(ResultMessages.success().add(
                ResultMessage.fromText(String.format(
                        "Article updated. Id is '%d'.", createdArticle
                                .getArticleId()))));

        return "article/saveForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, params = "remove")
    public String remove(@Validated({ ArticleDelete.class }) ArticleForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "article/saveForm";
        }

        articleService.delete(form.getArticleId());

        model.addAttribute(ResultMessages.success().add(
                ResultMessage.fromText(String.format(
                        "Article deleted. Id is '%d'.", form.getArticleId()))));

        return "article/saveForm";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public ModelAndView handleDuplicateKeyException(DuplicateKeyException e) {
        ExtendedModelMap model = new ExtendedModelMap();
        model.addAttribute(new ArticleForm());
        model.addAttribute(ResultMessages.error().add(
                ResultMessage.fromText(String.format("Already Created."))));
        return new ModelAndView("article/saveForm", model);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PessimisticLockingFailureException.class)
    public ModelAndView handlePessimisticLockingFailureException(
            PessimisticLockingFailureException e) {
        ExtendedModelMap model = new ExtendedModelMap();
        model.addAttribute(new ArticleForm());
        model.addAttribute(ResultMessages.error().add(
                ResultMessage.fromText(String.format("Already Updated."))));
        return new ModelAndView("article/saveForm", model);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ModelAndView handleOptimisticLockingFailureException(
            OptimisticLockingFailureException e) {
        ExtendedModelMap model = new ExtendedModelMap();
        model.addAttribute(new ArticleForm());
        model.addAttribute(ResultMessages.error().add(
                ResultMessage.fromText(String
                        .format("Already Updated or Deleted."))));
        return new ModelAndView("article/saveForm", model);
    }

}
