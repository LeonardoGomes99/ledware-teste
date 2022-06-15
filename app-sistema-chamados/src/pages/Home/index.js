import React, { useState,useEffect } from 'react';
import { Link,useNavigate } from 'react-router-dom'
import api from '../../services/api';
import './home.css';




function Home() {

    const [nomeRegister, setNomeRegister] = useState();
    const [emailRegister, setEmailRegister] = useState();
    const [senhaRegister, setSenhaRegister] = useState();

    const [emailLogin, setEmailLogin] = useState();
    const [senhaLogin, setSenhaLogin] = useState();
    const navigate = useNavigate();
    const Swal = require('sweetalert2')


    useEffect(() => {

      function sessionCheck() {         
        if(window.sessionStorage.getItem("usuarioId")){
          navigate("/dashboard");
        };
      }
      sessionCheck();
    },[])

    function register(){
      const params = {
        "nome" : nomeRegister,
        "email" : emailRegister,
        "senha" : senhaRegister,
      }

      api.post('/usuario',params).then(function(response){
        window.sessionStorage.setItem("usuarioId", response.data.id);
        navigate("/dashboard");
        
      }).catch(function(response){
        Swal.fire('Erro','Não foi possivel registrar','error');
      });

    }

    function login(){
      const params = {
        "email" : emailLogin,
        "senha" : senhaLogin,
      }


      api.post('/usuario/login',params).then(function(response){
        window.sessionStorage.setItem("usuarioId", response.data.id);
        navigate("/dashboard");
        
      }).catch(function(response){
        Swal.fire('Erro','Login inválido','error');
      });

    }

    return (
      <div className='home-content'>

          <div className='main'>  	
            <input type="checkbox" id="chk" aria-hidden="true"/>

              <div className='signup'>
                  <label class='home-label-register' for="chk" aria-hidden="true">Criar Conta</label>
                  <input className='home-input' type="text" value={nomeRegister} onChange={(e) => setNomeRegister(e.target.value)} name="nome" placeholder="Nome" required=""/>
                  <input className='home-input' type="email" value={emailRegister} onChange={(e) => setEmailRegister(e.target.value)} name="email" placeholder="Email" required=""/>
                  <input className='home-input' type="password" value={senhaRegister} onChange={(e) => setSenhaRegister(e.target.value)} name="senha" placeholder="Senha" required=""/>
                  <button className='home-button' onClick={() => register()}>Criar Conta</button>
              </div>

              <div className='login'>
                  <label class='home-label-login' for="chk" aria-hidden="true">Login</label>
                  <input className='home-input' type="email" value={emailLogin} onChange={(e) => setEmailLogin(e.target.value)} name="email" placeholder="Email" required="" />
                  <input className='home-input' type="password" value={senhaLogin} onChange={(e) => setSenhaLogin(e.target.value)} name="senha" placeholder="Senha" required="" />
                  <button className='home-button' onClick={() => login()} >Login</button>
              </div>
          </div>

      </div>      
    );
  }
  
  export default Home;
  