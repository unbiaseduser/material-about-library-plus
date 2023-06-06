package com.sixtyninefourtwenty.materialaboutlibrary.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class AboutList {

    private final List<AboutCard> aboutCards = new ArrayList<>();

    private AboutList(@NonNull Builder builder) {
        aboutCards.addAll(builder.aboutCards);
    }

    public AboutList(@NonNull AboutCard... cards) {
        Collections.addAll(aboutCards, cards);
    }

    public AboutList addCard(@NonNull AboutCard aboutCard) {
        aboutCards.add(aboutCard);
        return this;
    }

    public AboutList clearCards() {
        aboutCards.clear();
        return this;
    }

    public List<AboutCard> getCards() {
        return aboutCards;
    }

    public static class Builder {
        private final List<AboutCard> aboutCards = new ArrayList<>();

        @NonNull
        public Builder addCard(@NonNull AboutCard aboutCard) {
            aboutCards.add(aboutCard);
            return this;
        }

        @NonNull
        public AboutList build() {
            return new AboutList(this);
        }
    }
}
