/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import java.util.ArrayList;

import exceptions.common.DuplicateException;

/**
 *
 * @author Viktor
 */
public interface Controller<T> {

  void showPresets();

    void build(ArrayList<T> values);

    void insert(T value) throws DuplicateException;

    void nextStep();

    void playPause();

    void prevStep();

    void remove(T value);

    void search(T value);

    void setStepping(Boolean t1);
    
    void setRate(double d);


}
