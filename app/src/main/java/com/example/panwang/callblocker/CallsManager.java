package com.example.panwang.callblocker;

import java.util.ArrayList;

public class CallsManager {
    static ArrayList<String> m_whiteCodes = new ArrayList<String>();

    static boolean IsTrashCall(String incomingNumber) {
        boolean result = false;
        int numberLen = incomingNumber.length();

        if (numberLen <= 8) {
            //
            result = true;
        }
        else if (incomingNumber.startsWith("0") && (numberLen == 12 || numberLen == 11)) {
            result = true;
            for (int i=0; i<m_whiteCodes.size(); i++) {
                if (incomingNumber.startsWith(m_whiteCodes.get(i))) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
