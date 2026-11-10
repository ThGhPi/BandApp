import { Component } from '@angular/core';
import { Form } from '../../../../../shared/components/form/form';
import { Input } from '../../../../../shared/components/forms/input/text-input';

@Component({
  selector: 'app-signin.page',
  imports: [Form, Input],
  templateUrl: './signin.page.html',
  styleUrl: './signin.page.css',
})
export class SigninPage {

}
