package ru.nsu.ccfit.malinovskii.MessageTypes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

	private StringProperty nickname;

	public Person(String nickname) {
		this.nickname = new SimpleStringProperty(nickname);
	}

	public StringProperty getNicknameProperty() {
		return nickname;
	}

	public String getNickname() {
		return nickname.get();
	}

	public void setNickname(String nickname) {
		this.nickname.set(nickname);
	}
}
