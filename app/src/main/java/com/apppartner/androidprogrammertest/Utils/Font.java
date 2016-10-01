package com.apppartner.androidprogrammertest.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Samurai on 9/30/16.
 */

public class Font  {

    public enum FontType{
        MachinatoBold{
            public String toString(){
                return "fonts/Jelloween - Machinato Bold.ttf";
            }
        },
        MachinatoBoldItalic{
            public String toString(){
                return "fonts/Jelloween - Machinato Bold Italic.ttf";
            }
        },

        MachinatoExtraLightItalic{
            public String toString(){
                return "fonts/Jelloween - Machinato Bold ExtraLight Italic.ttf";
            }
        },

        MachinatoExtraLight{
            public String toString(){
                return "fonts/Jelloween - Machinato ExtraLight.ttf";
            }
        },

        MachinatoItalic{
            public String toString(){
                return "fonts/Jelloween - Machinato Italic.ttf";
            }
        },

        MachinatoLightItalic{
            public String toString(){
                return "fonts/Jelloween - Machinato Bold Light Italic.ttf";
            }
        },

        MachinatoLight{
            public String toString(){
                return "fonts/Jelloween - Machinato Light.ttf";
            }
        },

        MachinatoSemiBold{
            public String toString(){
                return "fonts/Jelloween - Machinato SemiBold.ttf";
            }
        },

        MachinatoSemiBoldItalic{
            public String toString(){
                return "fonts/Jelloween - Machinato SemiBold Italic.ttf";
            }
        },

        Machinato{
            public String toString(){
                return "fonts/Jelloween - Machinato.ttf";
            }
        }

    }

    public static Typeface setFont(Context mContext, String asset){
        return Typeface.createFromAsset(mContext.getAssets(), asset);
    }
}
