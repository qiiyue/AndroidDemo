// ICalculator.aidl
package cn.qiiyue.aidlserver;

import cn.qiiyue.aidlserver.MessageBean;

// Declare any non-default types here with import statements

interface ICalculator {

    /**
     * 加法计算
     */
    int add(int a, int b);

    MessageBean getMessageBean();

    void messageIn(in MessageBean msg);

    void messageOut(out MessageBean msg);

    void messageInOut(inout MessageBean msg);
}
