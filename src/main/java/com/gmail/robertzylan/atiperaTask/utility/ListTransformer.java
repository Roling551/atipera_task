package com.gmail.robertzylan.atiperaTask.utility;

import java.util.List;
import java.util.function.Function;

public interface ListTransformer {
	<T,R> List<R> transform(List<T> list, Function<T,R> transformFunction);
}
