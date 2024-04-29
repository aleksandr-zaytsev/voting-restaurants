package ru.azaytsev.votingrestaurants.common;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}