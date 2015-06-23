package com.futureinst.net;

public enum SingleEventScope {
	/**
	 * scope的取值如下：
	 *  base,表示事件基本信息；
	 *  price，事件的价格走势、三档最优信息；
	 * related,相关信息，包括参考信息、相关事件(暂无)、评论（暂无）；
	 *  lazybag,懒人包
	 */
	base,
	price,
	related,
	lazybag;
}
