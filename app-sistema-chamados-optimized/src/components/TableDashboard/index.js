import { Component } from 'react';
import { Link,useNavigate } from 'react-router-dom'
import './TableDashboard.css'

export default class TableDashboard extends Component {
    render(){
        return(
            <div>
                <table>
                    <tr>
                        <th>NOME</th>
                        <th>SOBRENOME</th>
                        <th>DATA</th>
                        <th>DESCRICAO</th>
                        <th>ACTIONS</th>
                    </tr>
                    <tr>
                        <td>LEO</td>
                        <td>GOMES</td>
                        <td>03/03/2011</td>
                        <td>DESCRICAO 1</td>
                        <td> DELETAR</td>
                    </tr>
                </table>
            </div>
        )
    }
    
}
    