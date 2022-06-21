import { Component } from 'react';
import { Link,useNavigate } from 'react-router-dom'
import './Header.css'

export default class Header extends Component {
    render(){
        return(
            <header>
                <h3 className="logo">Sistema de Chamados</h3>
                <Link className="buttons" to="/dashboard">Meus Chamados</Link>
                <Link className="buttons" to="/">Sair</Link>
            </header>
        )
    }
    
}
    