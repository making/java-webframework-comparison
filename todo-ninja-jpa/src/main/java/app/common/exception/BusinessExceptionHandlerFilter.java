package app.common.exception;

import java.util.Arrays;

import ninja.Result;
import ninja.Results;
import domain.common.exception.BusinessException;

public class BusinessExceptionHandlerFilter extends
		ExceptionHandlerFilter<BusinessException> {

	@Override
	protected Result handleException(BusinessException e) {
		ErrorModel errorModel = new ErrorModel(Arrays.asList(e.getMessage()));
		return Results.status(406).json().render(errorModel);
	}

}
