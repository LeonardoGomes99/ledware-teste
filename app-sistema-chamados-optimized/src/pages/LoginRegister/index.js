import React, { useState, useEffect } from 'react';
import './LoginRegister.css';
export default function LoginRegister() {

    const [nomeRegister, setNomeRegister] = useState();
    const [emailRegister, setEmailRegister] = useState();
    const [senhaRegister, setSenhaRegister] = useState();

    const [emailLogin, setEmailLogin] = useState();
    const [senhaLogin, setSenhaLogin] = useState();

    function register(){

    }

    function login(){
        
    }

    return(
        <div className='home-content'>
            <div className='main'>  	
            <input type="checkbox" id="chk" aria-hidden="true"/>

                <div className='signup'>
                    <label class='home-label-register' for="chk" aria-hidden="true">Criar Conta</label>
                    <input className='home-input' type="text" value={nomeRegister} onChange={(e) => setNomeRegister(e.target.value)} name="nome" placeholder="Nome" required=""/>
                    <input className='home-input' type="email" value={emailRegister} onChange={(e) => setEmailRegister(e.target.value)} name="email" placeholder="Email" required=""/>
                    <input className='home-input' type="password" value={senhaRegister} onChange={(e) => setSenhaRegister(e.target.value)} name="senha" placeholder="Senha" required=""/>
                    <button className='home-button'>Criar Conta</button>
                </div>

                <div className='login'>
                    <label class='home-label-login' for="chk" aria-hidden="true">Login</label>
                    <input className='home-input' type="email" value={emailLogin} onChange={(e) => setEmailLogin(e.target.value)} name="email" placeholder="Email" required="" />
                    <input className='home-input' type="password" value={senhaLogin} onChange={(e) => setSenhaLogin(e.target.value)} name="senha" placeholder="Senha" required="" />
                    <button className='home-button'>Login</button>
                </div>
            </div>
        </div>    
    )
}
