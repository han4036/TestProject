package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoggerInterceptor extends HandlerInterceptorAdapter {
//	implements HandlerInterceptor {
	
	
	// ======================================================== //
	// HandlerInterceptor를 implements 할경우
	// ======================================================== //
	/* @Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.debug("preHandler(req, res, handler) invoked.");
		log.info("\t+ handler: " + handler);
		log.info("\t  └ type: " + handler.getClass().getName());
	
		return true;
	
	@Override
	public void postHandle(
			HttpServletRequest req, HttpServletResponse res, 
			Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("postHandle(req, res, handler, modelAndView) invoked.");
		
		log.info("\t+ handler: " + handler);
		log.info("\t  └ type: " + handler.getClass().getName());
		
		log.info("\t+ modelAndView: " + modelAndView);
		
	} // postHandle
	
	@Override
	public void afterCompletion(
			HttpServletRequest req, HttpServletResponse res, 
			Object handler, Exception e) throws Exception {
		log.debug("afterCompletion(req, res, handler, e) invoked.");
		
		log.info("\t+ handler: " + handler);
		log.info("\t  └ type: " + handler.getClass().getName());
		
		log.info("\t+ e: " + e);
		
	} // afterCompletion */
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.debug("===========================================================");
		log.debug("		preHandle(req, res, handler) invoked.");
		log.debug("		Request URI \t : " + req.getRequestURI());
		log.debug("===========================================================");
		
		return super.preHandle(req, res, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("===========================================================");
		log.debug("		postHandle(req, res, handler) invoked.");
		log.debug("===========================================================");
	}
	
}
