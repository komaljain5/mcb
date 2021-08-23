export class SignupRequest {
    username: string;
    password: string;
    email: string;
    roles:any = [];

public constructor(username,password, email, roles){
        this.username=username;
        this.password=password;
        this.email=email;
        this.roles=roles;
    }
}