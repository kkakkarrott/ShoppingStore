import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

import { ApiAiClient } from 'api-ai-javascript';

import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

import {ActivatedRoute, Router} from '@angular/router';
import {ProductInOrder} from './models/ProductInOrder';
import {ProductInfo} from './models/productInfo';
import {CartService} from './services/cart.service';
import {ProductService} from './services/product.service';

export class Message {
  constructor(public content: string, public sentBy: string) {}
}

@Injectable()
export class ChatService {

  readonly token = environment.dialogflow.angularBot;
  readonly client = new ApiAiClient({ accessToken: this.token });

  conversation = new BehaviorSubject<Message[]>([]);

  constructor(private cartService: CartService,
	      private router: Router) {
      
  }

  // Sends and receives messages via DialogFlow
  converse(msg: string) {
    const userMessage = new Message(msg, 'user');
    this.update(userMessage);
    
    let callAPI = true;
    msg = msg.toLowerCase();
    if(msg.includes("add") && msg.includes("cart"))
    	{
    	callAPI = false;
    	msg = msg.replace(" add ","");
    	msg = msg.replace(" cart ","");
    	console.log(msg);
    	}
    if(callAPI)
    {
    return this.client.textRequest(msg)
               .then(res => {
                  const speech = res.result.fulfillment.speech;
                  const botMessage = new Message(speech, 'bot');
                  this.update(botMessage);
               });
    }
    else
    	{
    	let itemDesc;
    	
    	var matches = msg.match(/(\d+)/);
    	let qty;
    	if(matches)
    	{
    		qty = matches[0];
    	}
    	else
    		qty = 1;
    	
    	if(matches.length > 1)
    		{
    		this.update(new Message("I am afraid I do not understand your request",'bot'));
    		return null;
    		}
    	if(matches)
    		{
    		var i;
    		for (i = 0; i < matches.length; i++) { 
    		msg =  msg.replace(" "+matches[i]+" ","");
    		}
    		
    		}
    	
//    	if(msg.includes("item") && msg.lastIndexOf("item")+4 < msg.length)
//    		{
//    	 msg = msg.substring(msg.lastIndexOf("item")+4, msg.length);
//    		}
//    	
    	msg = msg.replace(" item ","");
    	msg = msg.replace(" my ","");
    	msg = msg.replace(" in ","");   	
    	msg = msg.replace(" to ","");
    	msg = msg.replace(" the ","");
    	msg = msg.replace(" a ","");
    	msg = msg.replace(" please ","");
    	
    	if(msg.length == 0 || msg.length == 1)
    		{
    		this.update(new Message("Kindly enter the correct item description",'bot'));
    		return null;
    		}
    	
    	console.log(itemDesc);
    	console.log(qty);
    	console.log(msg);
    	
    	
    	
    	let productInfo = new ProductInfo();
    	productInfo.productId = "B0001";
    	productInfo.productName = msg;
    	this.cartService
        .addItem(new ProductInOrder(productInfo, qty))
        .subscribe(
            res => {
              if (!res) {
                console.log('Add Cart failed' + res);
                throw new Error();
              }
              this.router.navigateByUrl('/cart');
            },
            _ => console.log('Add Cart Failed')
       );
    	
    	this.update(new Message("Your item has been added to the cart",'bot'));
    	}
    return null;
  }



  // Adds message to source
  update(msg: Message) {
    this.conversation.next([msg]);
  }


}
