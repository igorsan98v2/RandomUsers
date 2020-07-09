package com.ygs.netronic.interfaces;

import java.util.List;

public interface Foreign  {
    void appendToLocalList(long foreignKeyId, List<? super Local> list);
}
