package com.me.core.exception;


//import static org.hzero.core.base.BaseConstants.DEFAULT_ENV;

import com.me.core.message.Message;
import com.me.core.message.MessageAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

import static com.me.core.base.BaseConstants.DEFAULT_ENV;
//
//import io.choerodon.core.exception.CommonException;
//import io.choerodon.core.exception.ExceptionResponse;
//import io.choerodon.core.exception.FeignException;
//import io.choerodon.core.exception.NotFoundException;
//import io.choerodon.core.oauth.CustomUserDetails;
//import io.choerodon.core.oauth.DetailsHelper;
//
//import org.hzero.core.base.BaseConstants;
//import org.hzero.core.message.Message;
//import org.hzero.core.message.MessageAccessor;


/**
 * 程序异常处理器
 *
 * @author jiangzhou.bo@hand-china.com 2018/06/14 11:19
 */
//@ControllerAdvice
@RestControllerAdvice
public class BaseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @Value("${spring.profiles.active:" + DEFAULT_ENV + "}")
    private String env;

//    @ExceptionHandler(FeignException.class)
//    public ResponseEntity<ExceptionResponse> processFeignException(HttpServletRequest request, HandlerMethod method, FeignException exception) {
//        if (LOGGER.isInfoEnabled()) {
//            LOGGER.info("Feign exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(MessageAccessor.getMessage(exception.getCode(), exception.getParameters()));
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, CommonException exception) {
        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn("Common exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
        }
        ExceptionResponse er = new ExceptionResponse();
//        ExceptionResponse er = new ExceptionResponse(MessageAccessor.getMessage(exception.getCode(), exception.getParameters()));
//        setDevException(er, exception);
        return new ResponseEntity<>(er, HttpStatus.OK);
    }
//
//    /**
//     * 拦截NotFound异常信息，将信息ID换为messages信息。
//     *
//     * @param exception 异常
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, NotFoundException exception) {
//        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn("Not found exception", exception);
//        }
//        ExceptionResponse er = new ExceptionResponse("error.resource.notExist");
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截处理 Valid 异常
//     *
//     * @param exception 异常
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, MethodArgumentNotValidException exception) {
//        if (LOGGER.isInfoEnabled()) {
//            LOGGER.info("Method arg invalid exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        String defaultMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        ExceptionResponse er = new ExceptionResponse(MessageAccessor.getMessage("error.methodArgument.notValid", defaultMessage));
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截处理 DuplicateKeyException 异常
//     *
//     * @param exception DuplicateKeyException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(DuplicateKeyException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, DuplicateKeyException exception) {
//        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn("Duplicate key exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse("error.db.duplicateKey");
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截处理 MultipartException 异常
//     *
//     * @param exception MultipartException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(MultipartException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, MultipartException exception) {
//        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn("Multipart exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse("error.upload.multipartSize");
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截处理 BadSqlGrammarException 异常
//     * 搜索接口排序字段错误，在这里拦截异常，并友好返回前端
//     *
//     * @param exception BadSqlGrammarException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(BadSqlGrammarException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, BadSqlGrammarException exception) {
//        if (LOGGER.isErrorEnabled()) {
//            LOGGER.error("Bad sql exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse("error.db.badSql");
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
    /**
     * 拦截 {@link MessageException} 异常信息，直接返回封装的异常消息
     *
     * @param exception MessageException
     * @return ExceptionResponse
     */
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, MessageException exception) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(exception.getMessage(), exception);
        }
        // 获取异常消息的警告类型：info、warn、error，默认为warn
        Message message = MessageAccessor.getMessage(exception.getCode());
        ExceptionResponse er = new ExceptionResponse(exception.getCode(), exception.getMessage(), message.getType());
        setDevException(er, exception);
        return new ResponseEntity<>(er, HttpStatus.OK);
    }
//
//    /**
//     * 拦截 {@link HystrixRuntimeException} 异常信息，返回 “网络异常，请稍后重试” 信息
//     *
//     * @param exception HystrixRuntimeException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler({HystrixRuntimeException.class})
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, HystrixRuntimeException exception) {
//        if (LOGGER.isErrorEnabled()) {
//            LOGGER.error("Hystrix exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(BaseConstants.ErrorCode.ERROR_NET);
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截 {@link IllegalArgumentException} 异常信息，返回 “数据校验不通过” 信息
//     *
//     * @param exception IllegalArgumentException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, RuntimeException exception) {
//        if (LOGGER.isErrorEnabled()) {
//            LOGGER.error("Unknown runtime exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(exception.getMessage());
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截 {@link OptimisticLockException} 异常信息，返回 “记录不存在或版本不一致” 信息
//     *
//     * @param exception OptimisticLockException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(OptimisticLockException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, OptimisticLockException exception) {
//        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn("Optimistic lock exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(BaseConstants.ErrorCode.OPTIMISTIC_LOCK);
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截 {@link NotLoginException} 异常信息，返回 “请登录后再进行操作” 信息
//     *
//     * @param exception NotLoginException
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(NotLoginException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, NotLoginException exception) {
//        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn(exception.getMessage(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(BaseConstants.ErrorCode.NOT_LOGIN);
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截 {@link RuntimeException} / {@link Exception} 异常信息，返回 “程序出现错误，请联系管理员” 信息
//     *
//     * @param exception 异常
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler({RuntimeException.class, Exception.class})
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, Exception exception) {
//        if (LOGGER.isErrorEnabled()) {
//            LOGGER.error("Unknown exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(BaseConstants.ErrorCode.ERROR);
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截 {@link CheckedException} 异常信息，返回 “程序出现错误，请联系管理员” 信息
//     *
//     * @param exception 异常
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(CheckedException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, CheckedException exception) {
//        if (LOGGER.isWarnEnabled()) {
//            LOGGER.warn("Checked exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(MessageAccessor.getMessage(exception.getMessage(), exception.getParameters()));
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    /**
//     * 拦截 {@link SQLException} 异常信息，返回 “数据操作错误，请联系管理员” 信息
//     *
//     * @param exception 异常
//     * @return ExceptionResponse
//     */
//    @ExceptionHandler(SQLException.class)
//    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, SQLException exception) {
//        if (LOGGER.isErrorEnabled()) {
//            LOGGER.error("Sql exception, request: {}, user: {}", requestInfo(request, method), currentUserInfo(), exception);
//        }
//        ExceptionResponse er = new ExceptionResponse(BaseConstants.ErrorCode.ERROR_SQL_EXCEPTION);
//        setDevException(er, exception);
//        return new ResponseEntity<>(er, HttpStatus.OK);
//    }
//
//    private String requestInfo(HttpServletRequest request, HandlerMethod method) {
//        return String.format("RequestInfo{URI=%s, method=%s}", request.getRequestURI(), method.toString());
//    }
//
//    private String currentUserInfo() {
//        return Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::simpleUserInfo).orElse("null");
//    }

    private void setDevException(ExceptionResponse er, Exception ex) {
        if (DEFAULT_ENV.equals(env)) {
            er.setException(ex.getMessage());
            er.setTrace(ex.getStackTrace());

            Throwable cause = ex.getCause();
            if (cause != null) {
                er.setThrowable(cause.getMessage(), cause.getStackTrace());
            }
        }
    }

}
