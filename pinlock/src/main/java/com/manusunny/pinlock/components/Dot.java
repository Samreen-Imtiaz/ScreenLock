/*
 * Copyright (C) 2015. Manu Sunny <manupsunny@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.manusunny.pinlock.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.LinearLayout;

import com.manusunny.pinlock.R;

public class Dot extends View {



    private TypedArray styledAttributes;

    public Dot(Context context, TypedArray styledAttributes, boolean filled) {
        super(context);
        this.styledAttributes = styledAttributes;
        setBackground(filled);
        setLayoutParameters();
    }


    private void setLayoutParameters() {
        final int dotDiameter = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_statusDotDiameter, 50);
        final int margin = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_statusDotSpacing, 30);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotDiameter, dotDiameter);
        params.setMargins(margin, 0, margin, 0);
        setLayoutParams(params);
    }

    private void setBackground(boolean filled) {
        if (filled) {
            final int background = styledAttributes
                    .getResourceId(R.styleable.PinLock_statusFilledBackground, R.drawable.dot_filled);
            setBackgroundResource(background);
        } else {
            final int background = styledAttributes
                    .getResourceId(R.styleable.PinLock_statusEmptyBackground, R.drawable.dot_empty);
            setBackgroundResource(background);
        }
    }
}
