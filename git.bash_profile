# assets <de1f> ✗ Ӽ <de1f> <dfa6> ✔ ✓ ▲ ➜

check_status() {

red="$bold$(tput setaf 1)"
green=$(tput setaf 2)

boshka= git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/ (\1)/' > /dev/null 2>&1


# Checks if something to commit or not
if git rev-parse --git-dir > /dev/null 2>&1; then
    if ! git status | grep "nothing to commit" > /dev/null 2>&1; then
            echo "${red} x "
            return 0
    elif $boshka; then
            echo "${green} ✓ "
    fi
fi

}

export PS1='[\t]$(check_status)\[\033[0;32m\]\[\033[0m\033[0;32m\]\u\[\033[0;36m\]@\w\[\033[0;32m\]$(__git_ps1 [%s])\$\[\033[0m\033[0;32m\] (●'◡'●)\[\033[0m\] '