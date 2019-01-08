package com.format.phone.phoneformatedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private int oldLength;
    private boolean isPhoneFormat;
    private boolean isMobileOrTelephone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edittext);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                oldLength = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String text = s.toString();

                if (text.length()==1) // check the first word that word is equal "+"
                {
                    if (text.equals("+"))
                    {
                        isPhoneFormat = false; //false = +66894768902 format (+66 is Country calling code of Thailand)
                    }
                    else {
                        isPhoneFormat = true; // true = 0894768902 format
                    }
                }

                if (isPhoneFormat && text.length()==2) //check Mobile phone Or Telephone
                {
                    checkMobileOrTelephone(text);
                }
                else if (!isPhoneFormat && text.length()==4)
                {
                    checkMobileOrTelephone(text);
                }

                if (text.endsWith("-"))
                    return;

                if (isPhoneFormat)
                {
                    if (isMobileOrTelephone)//telephone
                    {
                        setPhoneFormat(text, 3,7,11);
                    }
                    else //mobile
                    {
                        setPhoneFormat(text, 4,8,12);
                    }
                }else //+66
                {
                    if (isMobileOrTelephone)
                    {
                        setPhoneFormat(text, 5,9,13);
                    }
                    else
                    {
                        setPhoneFormat(text, 6,10,14);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (oldLength>s.toString().length())
                {
                    if (s.toString().endsWith("-")) // auto delete "-"
                    {
                        editText.setText(s.toString().substring(0,s.toString().length()-1));
                        editText.setSelection(editText.getText().length());

                    }
                }
            }
        });
    }

    private void checkMobileOrTelephone(String textNumber)
    {
        // in Thailand , phone number are start with 02,03,04,05,07 is Telephone
        if (textNumber.contains("2")||textNumber.contains("3")||textNumber.contains("4")||textNumber.contains("5")||textNumber.contains("7")) // telephone
        {
            isMobileOrTelephone = true;
        }
        else // mobile phone
        {
            isMobileOrTelephone = false;
        }
    }

    private void setPhoneFormat(String text, int digistOne, int digistTwo, int input)
    {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(input)}); // set max input , telephon and mobile phone have different length
        if (text.length() == digistOne)
        {
            editText.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString()); // insert "-"
            editText.setSelection(editText.getText().length());
        }
        else if (text.length() == digistTwo)
        {
            editText.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
            editText.setSelection(editText.getText().length());
        }

        // you can if-else or switch case for insert this word "-"
    }
}
