package app.common.exception;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

public abstract class ExceptionHandlerFilter<T extends Exception> implements
		Filter {
	@Override
	public final Result filter(FilterChain filterChain, Context context) {
		try {
			return filterChain.next(context);
		} catch (Exception e) {
			return handleException((T) e);
		}
	}

	abstract protected Result handleException(T e);
}
