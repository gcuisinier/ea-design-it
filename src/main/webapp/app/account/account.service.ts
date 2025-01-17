import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';

export default class AccountService {
  public anonymousReadAllowed = true;
  public initialized = false;

  constructor(private store: Store<any>, private router: VueRouter) {
    this.init();
  }

  public init(): void {
    this.retrieveProfiles();
    this.retrieveAnonymousProperty();
    // remember me...
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    if (!this.store.getters.account && !this.store.getters.logon && token) {
      this.retrieveAccount();
    }
  }

  public retrieveProfiles(): Promise<boolean> {
    return new Promise(resolve => {
      axios
        .get<any>('management/info')
        .then(res => {
          if (res.data && res.data.activeProfiles) {
            this.store.commit('setRibbonOnProfiles', res.data['display-ribbon-on-profiles']);
            this.store.commit('setActiveProfiles', res.data['activeProfiles']);
          }
          resolve(true);
        })
        .catch(() => resolve(false));
    });
  }

  public retrieveAnonymousProperty(): Promise<boolean> {
    console.log('About to call api/account/anoymous-reader');
    return new Promise(resolve => {
      axios
        .get<any>('api/account/anoymous-reader')
        .then(res => {
          this.anonymousReadAllowed = res.data;
          this.initialized = true;
          console.log('api/account/anoymous-reader : ' + this.anonymousReadAllowed);
          resolve(this.anonymousReadAllowed);
        })
        .catch(() => {
          this.initialized = true;
          resolve(this.anonymousReadAllowed);
        });
    });
  }

  public retrieveAccount(): Promise<boolean> {
    return new Promise(resolve => {
      axios
        .get<any>('api/account')
        .then(response => {
          this.store.commit('authenticate');
          const account = response.data;
          if (account) {
            this.store.commit('authenticated', account);
            if (sessionStorage.getItem('requested-url')) {
              this.router.replace(sessionStorage.getItem('requested-url'));
              sessionStorage.removeItem('requested-url');
            }
          } else {
            this.store.commit('logout');
            if (this.router.currentRoute.path !== '/') {
              this.router.push('/');
            }
            sessionStorage.removeItem('requested-url');
          }
          resolve(true);
        })
        .catch(() => {
          this.store.commit('logout');
          resolve(false);
        });
    });
  }

  public hasAnyAuthorityAndCheckAuth(authorities: any): Promise<boolean> {
    if (typeof authorities === 'string') {
      authorities = [authorities];
    }

    if (!this.authenticated || !this.userAuthorities) {
      const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
      if (!this.store.getters.account && !this.store.getters.logon && token) {
        return this.retrieveAccount().then(resp => {
          if (resp) {
            return this.checkAuthorities(authorities);
          }
          return Promise.resolve(false);
        });
      }
      return Promise.resolve(false);
    }
    return this.checkAuthorities(authorities);
  }

  private checkAuthorities(authorities: any): Promise<boolean> {
    if (this.userAuthorities) {
      for (const authority of authorities) {
        if (this.userAuthorities?.includes(authority)) {
          return Promise.resolve(true);
        }
      }
    }
    return Promise.resolve(false);
  }

  public get authenticated(): boolean {
    return this.store.getters.authenticated;
  }

  public get userAuthorities(): any {
    return this.store.getters.account?.authorities;
  }

  public get readAuthorities(): boolean {
    if (this.anonymousReadAllowed) {
      //anonymous read
      return true;
    } else {
      return this.store.getters.userAuthority;
    }
  }

  public get writeAuthorities(): boolean {
    return this.store.getters.writeAuthority;
  }

  public get deleteAuthorities(): boolean {
    return this.store.getters.deleteAuthority;
  }

  public get contributorAuthorities(): boolean {
    return this.store.getters.contributorAuthority;
  }

  public get writeOrContributor(): boolean {
    return this.writeAuthorities || this.contributorAuthorities;
  }
}
