import { Component } from '@angular/core';
import { IonicPage, NavController, ToastController } from 'ionic-angular';

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html'
})
export class LoginPage {
  account: { username: string, password: string } = {
    username: 'user',
    password: 'password'
  };

  constructor(public navCtrl: NavController, public toastCtrl: ToastController) {
    // TODO
  }

  // Attempt to login in through our User service
  doLogin() {
    let toast = this.toastCtrl.create({
      message: 'Not yet implemented!',
      duration: 3000,
      position: 'top'
    });
    toast.present();
  }
}
