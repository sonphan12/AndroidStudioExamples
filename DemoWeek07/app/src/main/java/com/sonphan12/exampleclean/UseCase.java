package com.sonphan12.exampleclean;

import android.content.Context;

public interface UseCase<T1, T2> {
    T2 executeUsecase(Context ctx, T1 param);
}
